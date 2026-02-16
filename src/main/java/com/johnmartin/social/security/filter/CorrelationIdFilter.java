package com.johnmartin.social.security.filter;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.social.constants.SecurityConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CorrelationIdFilter extends BaseFilter {

    public CorrelationIdFilter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void doFilterAction(HttpServletRequest request, HttpServletResponse response) {
        String requestId = request.getHeader(SecurityConstants.REQUEST_ID);

        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(SecurityConstants.REQUEST_ID, requestId);
        request.setAttribute(SecurityConstants.REQUEST_ID, requestId);
        response.setHeader(SecurityConstants.REQUEST_ID, requestId);
    }

    @Override
    protected void afterRequest() {
        MDC.remove(SecurityConstants.REQUEST_ID);
    }
}
