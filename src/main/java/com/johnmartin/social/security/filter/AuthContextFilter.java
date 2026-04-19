package com.johnmartin.social.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.SecurityConstants;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.error.AuthErrorConstants;
import com.johnmartin.social.dto.AuthUser;
import com.johnmartin.social.dto.internal.AuthUserResponse;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.mapper.UserMapper;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.service.client.AuthServiceClient;
import com.johnmartin.social.utilities.LoggerUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthContextFilter extends BaseFilter {

    private static final Class<AuthContextFilter> clazz = AuthContextFilter.class;

    private final AuthServiceClient authService;

    public AuthContextFilter(AuthServiceClient authService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.authService = authService;
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestId = (String) request.getAttribute(SecurityConstants.HttpHeaders.REQUEST_ID);

        if (StringUtils.isBlank(authHeader)) {
            throw new UnauthorizedException(AuthErrorConstants.MISSING_AUTHENTICATION_HEADER);
        }

        LoggerUtility.d(clazz, String.format("requestId: [%s]", requestId));

        // Auth details will be handled in AuthService
        AuthUserResponse authUserResponse = authService.validate(authHeader, requestId);
        LoggerUtility.d(clazz, String.format("authUser: [%s]", authUserResponse));

        // Added entity with same fields for design
        AuthUser authUser = UserMapper.toAuthUser(authUserResponse);
        AuthContext.set(authUser);

        // REQUIRED: put authUser to Spring Security Context
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUser,
                                                                                                     null,
                                                                                                     null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected void afterRequest() {
        AuthContext.clear();
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return uri.startsWith(ApiConstants.Path.ACTUATOR)
               || uri.startsWith(ApiConstants.InternalPath.API_USER_INTERNAL);
    }
}
