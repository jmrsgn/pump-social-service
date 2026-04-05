package com.johnmartin.social.service;

import org.springframework.stereotype.Service;

import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.utilities.LoggerUtility;

@Service
public class AuthService {

    private static final Class<AuthService> clazz = AuthService.class;

    /**
     * Get auth user from AuthContext
     *
     * @return AuthUser
     */
    public AuthUser getAuthUser() {
        LoggerUtility.d(clazz, "Execute method: [getAuthUser]");
        AuthUser authUser = AuthContext.get();
        if (authUser == null) {
            LoggerUtility.d(clazz, "Auth user is null, will throw unauthorized exception");
            throw new UnauthorizedException("User is not authenticated");
        }
        return authUser;
    }
}
