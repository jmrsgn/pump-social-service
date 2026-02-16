package com.johnmartin.social.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.constants.api.ApiErrorMessages;
import com.johnmartin.social.exception.UnauthorizedException;
import com.johnmartin.social.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends BaseFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    public AuthenticationFilter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException(ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED);
        }
    }

    @Override
    protected boolean shouldSkip(HttpServletRequest request) {
        return request.getRequestURI().startsWith(ApiConstants.Path.ACTUATOR);
    }
}
