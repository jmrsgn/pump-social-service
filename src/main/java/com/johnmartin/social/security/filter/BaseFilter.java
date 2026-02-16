package com.johnmartin.social.security.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.response.ApiErrorResponse;
import com.johnmartin.social.dto.response.Result;
import com.johnmartin.social.exception.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    public BaseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected final void doFilterInternal(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filterChain) throws ServletException, IOException {
        try {
            if (shouldSkip(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            doFilterAction(request, response);
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException ex) {
            writeUnauthorized(response, ex.getMessage());
        } finally {
            afterRequest();
        }
    }

    /**
     * Each filter implements ONLY this
     */
    protected abstract void doFilterAction(HttpServletRequest request, HttpServletResponse response) throws IOException,
                                                                                                     ServletException;

    /**
     * Override if a filter wants to skip certain paths
     */
    protected boolean shouldSkip(HttpServletRequest request) {
        return false;
    }

    /**
     * Override if cleanup is needed
     */
    protected void afterRequest() {
        // Do nothing
    }

    protected void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiErrorResponse error = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                      ApiConstants.Error.UNAUTHORIZED,
                                                      message);

        response.getWriter().write(objectMapper.writeValueAsString(Result.failure(error)));
    }
}
