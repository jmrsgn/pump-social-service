package com.johnmartin.social.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.SecurityConstants;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthContextFilter extends BaseFilter {

    private final AuthService authService;

    public AuthContextFilter(AuthService authService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.authService = authService;
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
        // IMPORTANT:
        // Do NOT throw on missing Authorization header.
        // Health probes and public endpoints rely on this.

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String requestId = (String) request.getAttribute(SecurityConstants.REQUEST_ID);

        if (StringUtils.isBlank(authHeader)) {
            throw new UnauthorizedException(ApiErrorMessages.MISSING_AUTH_HEADER);
        }

        // Auth details will be handled in AuthService
        AuthContext.set(authService.validate(authHeader, requestId));
    }

    @Override
    protected void afterRequest() {
        AuthContext.clear();
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        return request.getRequestURI().startsWith(ApiConstants.Path.ACTUATOR);
    }
}
