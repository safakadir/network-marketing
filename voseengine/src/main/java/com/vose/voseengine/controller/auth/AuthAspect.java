package com.vose.voseengine.controller.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.vose.voseengine.model.entity.BayiRelatable;
import com.vose.voseengine.Util;
import com.vose.voseengine.controller.HelperUtil;
import com.vose.voseengine.model.service.JwtClaims;
import com.vose.voseengine.model.service.Login;
import com.vose.voseengine.service.BayiService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {
    private static final Logger log = LoggerFactory.getLogger(AuthAspect.class);

    @Autowired
    private BayiService bayiService;

    @Value("${security.jwtSecret}")
    private String secret;

    @Value("${security.jwtIssuer}")
    private String issuer;

    private JWTVerifier jwtVerifier;

    @Pointcut("@annotation(Authorize)")
    private void authorizedMethods() {}

    @Before("authorizedMethods()")
    public void checkAuthorization(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authorize authAnnotation = signature.getMethod().getAnnotation(Authorize.class);

        if(authAnnotation.type().equalsIgnoreCase("PUBLIC")) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        JwtClaims userInfo = authenticate(request);
        request.setAttribute("userInfo", userInfo);

        if(authAnnotation.type().equalsIgnoreCase("ADMIN")) {
            if(!userInfo.getType().equalsIgnoreCase(Login.Type.ADMIN.name())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot access admin only service");
            }
        }
        else { // BAYI or BAYICHAIN
            if(userInfo.getType().equalsIgnoreCase(Login.Type.ADMIN.name())) {
                return;
            }

            Long pathBayiId = HelperUtil.bayiIdFromPath(request.getServletPath());
            if(pathBayiId != null && !checkBayiAccess(userInfo.getId(), pathBayiId, authAnnotation.type()) ) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot access other user's entity");
            }
            for(Object arg : joinPoint.getArgs()) {
                if(arg instanceof BayiRelatable) {
                    Long objectBayiId = ((BayiRelatable)arg).getBayiId();
                    if(objectBayiId != null && !checkBayiAccess(userInfo.getId(), objectBayiId, authAnnotation.type())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User cannot access other user's entity");
                    }
                }
            }
        }
    }

    @PostConstruct
    private void init() {
        log.info("Initializing AuthorizationAspect");
        Algorithm algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build(); //Reusable verifier instance
    }

    private JwtClaims authenticate(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(Util.isNullOrEmpty(authHeader)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty authorization header.");
        }
        String[] authHeaderParts = authHeader.split(" ");
        if(authHeaderParts.length != 2 || !authHeaderParts[0].equals("Bearer")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header.");
        }
        String accessToken = authHeaderParts[1];

        JwtClaims userInfo = null;
        try {
            userInfo = JwtClaims.fromJwt(jwtVerifier.verify(accessToken));
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        if(!userInfo.getAction().equalsIgnoreCase("access")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid action type, should be access");
        }

        return userInfo;
    }

    private boolean checkBayiAccess(Long userBayiId, Long requestedBayiId, String authType) {
        if(userBayiId.longValue() == requestedBayiId.longValue()) return true;
        if(authType.equalsIgnoreCase("BAYICHAIN")) {
            return bayiService.isAltBayi(userBayiId, requestedBayiId);
        }
        return false;
    }
}
