package com.vose.voseengine.controller;

import com.vose.voseengine.Util;
import com.vose.voseengine.controller.auth.Authorize;
import com.vose.voseengine.model.service.ChangePasswordRequest;
import com.vose.voseengine.model.service.LoginCredentials;
import com.vose.voseengine.service.AuthService;
import com.vose.voseengine.model.service.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public Login bayiLogin(@RequestBody LoginCredentials credentials) {
        return authService.bayiLogin(credentials);
    }

    @PostMapping("/admin")
    public Login adminLogin(@RequestBody LoginCredentials credentials) {
        return authService.adminLogin(credentials);
    }

    @GetMapping
    public Login refreshLogin(@RequestHeader("Authorization") String authHeader) {
        String[] authHeaderParts = authHeader.split(" ");
        if(authHeaderParts.length != 2 || !authHeaderParts[0].equals("Bearer")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header.");
        }
        return authService.refreshLogin(authHeaderParts[1]);
    }

    @PutMapping("/password")
    @Authorize(type = "BAYI")
    public boolean changePassword(@RequestBody ChangePasswordRequest request) {
        long userId = HelperUtil.userIdFromContext();
        return authService.changePassword(request, userId, HelperUtil.isAdminFromContext());
    }
}
