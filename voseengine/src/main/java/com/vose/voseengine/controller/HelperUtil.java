package com.vose.voseengine.controller;

import com.vose.voseengine.model.service.JwtClaims;
import com.vose.voseengine.model.service.Login;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HelperUtil {
    public static Long bayiIdFromPath(String path) {
        Long bayiId = null;
        int index = path.indexOf("/bayi/");
        if(index > -1) {
            String remainingPath = path.substring(index+6);
            int closeIndex = remainingPath.indexOf('/');
            String bayiIdStr;
            if(closeIndex > -1) bayiIdStr = remainingPath.substring(0, closeIndex);
            else bayiIdStr = remainingPath;
            try {
                bayiId = Long.parseLong(bayiIdStr);
            }
            catch (Exception e) {}
        }
        return bayiId;
    }

    public static Long userIdFromContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ((JwtClaims)request.getAttribute("userInfo")).getId();
    }

    public static boolean isAdminFromContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ((JwtClaims)request.getAttribute("userInfo")).getType().equalsIgnoreCase(Login.Type.ADMIN.name());
    }

    public static Object getContextAttribute(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getAttribute(key);
    }
}
