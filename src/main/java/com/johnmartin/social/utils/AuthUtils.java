package com.johnmartin.social.utils;

import com.johnmartin.social.constants.SecurityConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.servlet.http.HttpServletRequest;

public final class AuthUtils {

    /**
     * Get authenticated user from boundary
     * 
     * @param request
     *            - Servlet request
     * @return AuthUser
     */
    public static AuthUser requireAuthUser(HttpServletRequest request) {
        LoggerUtility.d(AuthUtils.class, "Execute method: [requireAuthUser]");
        AuthUser user = (AuthUser) request.getAttribute(SecurityConstants.AUTH_USER);
        if (user == null) {
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }
        return user;
    }
}
