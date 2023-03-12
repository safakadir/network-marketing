package com.vose.voseengine.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.vose.voseengine.controller.HelperUtil;
import com.vose.voseengine.model.entity.Admin;
import com.vose.voseengine.model.entity.Bayi;
import com.vose.voseengine.model.service.ChangePasswordRequest;
import com.vose.voseengine.model.service.JwtClaims;
import com.vose.voseengine.model.service.LoginCredentials;
import com.vose.voseengine.repository.AdminRepository;
import com.vose.voseengine.repository.BayiRepository;
import com.vose.voseengine.model.service.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private BayiRepository bayiRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Value("${security.jwtSecret}")
    private String secret;

    @Value("${security.jwtIssuer}")
    private String issuer;

    @Value("${security.jwtAccessExpireHour}")
    private int accessExpireHour;

    @Value("${security.jwtRefreshExpireHour}")
    private int refreshExpireHour;

    private JWTVerifier jwtVerifier;

    @PostConstruct
    private void init() {
        log.info("Initializing AuthService");
        Algorithm algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build(); //Reusable verifier instance
    }

    public Login bayiLogin(LoginCredentials credentials) {
        Bayi bayi = bayiRepository.findByIdAndSifre(credentials.getUsernameLong(), credentials.getPassword());
        if(bayi == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return generateLogin(bayi);
    }

    public Login adminLogin(LoginCredentials credentials) {
        Admin admin = adminRepository.findByEmailAndSifre(credentials.getUsername(), credentials.getPassword());
        if(admin == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return generateLogin(admin);
    }

    public Login refreshLogin(String refreshToken) {
        JwtClaims userInfo = null;
        try {
            userInfo = JwtClaims.fromJwt(jwtVerifier.verify(refreshToken));
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if(!userInfo.getAction().equals("refresh")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid action type, should be refresh");
        }
        try {
            if (userInfo.getType().equals(Login.Type.ADMIN.name())) {
                Admin admin = adminRepository.findById(userInfo.getId()).get();
                return generateLogin(admin);
            } else if (userInfo.getType().equals(Login.Type.BAYI.name())) {
                Bayi bayi = bayiRepository.findById(userInfo.getId()).get();
                return generateLogin(bayi);
            }
            else {
                throw new IllegalArgumentException("Wrong type: "+userInfo.getType());
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public boolean changePassword(ChangePasswordRequest request, long userId, boolean isAdmin) {
        if(isAdmin) {
            if(request.getBayiId() > 0) {
                bayiRepository.changePassword(request.getBayiId(), request.getNewPassword());
            }
            else {
                adminRepository.changePassword(userId, request.getNewPassword());
            }
        }
        else {
            if(request.getBayiId() > 0 && request.getBayiId() != userId) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot change other one's password!");
            }
            Bayi bayi = bayiRepository.findByIdAndSifre(userId, request.getOldPassword());
            if(bayi == null) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credentials wrong");
            }
            bayiRepository.changePassword(bayi.getId(), request.getOldPassword(), request.getNewPassword());
        }
        return true;
    }

    private Login generateLogin(Bayi bayi) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        long now = new Date().getTime();
        long accessExpiresAt = now + accessExpireHour*60L*60L*1000L;
        long refreshExpiresAt = now + refreshExpireHour*60L*60L*1000L;
        String accessToken = JWT.create()
                .withIssuer(issuer)
                .withClaim("type", Login.Type.BAYI.name())
                .withClaim("action", "access")
                .withClaim("id", bayi.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(accessExpiresAt))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withIssuer(issuer)
                .withClaim("type", Login.Type.BAYI.name())
                .withClaim("action", "refresh")
                .withClaim("id", bayi.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(refreshExpiresAt))
                .sign(algorithm);
        return new Login(bayi, accessToken, refreshToken, accessExpiresAt, refreshExpiresAt);
    }

    private Login generateLogin(Admin admin) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        long now = new Date().getTime();
        long accessExpiresAt = now + accessExpireHour*60L*60L*1000L;
        long refreshExpiresAt = now + refreshExpireHour*60L*60L*1000L;
        String accessToken = JWT.create()
                .withIssuer(issuer)
                .withClaim("type", Login.Type.ADMIN.name())
                .withClaim("action", "access")
                .withClaim("id", admin.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(accessExpiresAt))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withIssuer(issuer)
                .withClaim("type", Login.Type.ADMIN.name())
                .withClaim("action", "refresh")
                .withClaim("id", admin.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(refreshExpiresAt))
                .sign(algorithm);
        return new Login(admin, accessToken, refreshToken, accessExpiresAt, refreshExpiresAt);
    }
}
