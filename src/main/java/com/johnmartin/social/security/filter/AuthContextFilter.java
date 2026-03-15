package com.johnmartin.social.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.SecurityConstants;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.security.AuthContext;
import com.johnmartin.social.service.AuthServiceClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthContextFilter extends BaseFilter {

    private final AuthServiceClient authService;

    public AuthContextFilter(AuthServiceClient authService, ObjectMapper objectMapper) {
        super(objectMapper);
        this.authService = authService;
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
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
        String uri = request.getRequestURI();

        return uri.startsWith(ApiConstants.Path.ACTUATOR)
               || uri.startsWith(ApiConstants.InternalPath.API_USER_INTERNAL);
    }
}
