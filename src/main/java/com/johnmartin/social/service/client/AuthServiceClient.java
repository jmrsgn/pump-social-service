package com.johnmartin.social.service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.johnmartin.social.constants.SecurityConstants;
import com.johnmartin.social.constants.UIConstants;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.internal.AuthUserResponse;
import com.johnmartin.social.dto.response.common.Result;
import com.johnmartin.social.exception.UnauthorizedException;

@Service
public class AuthServiceClient {

    private final RestClient authWebClient;

    public AuthServiceClient(RestClient authWebClient) {
        this.authWebClient = authWebClient;
    }

    @Retryable(retryFor = Exception.class, maxAttempts = ApiConstants.RETRIES_COUNT, backoff = @Backoff(delay = UIConstants.DELAY_2000))
    public AuthUserResponse validate(String authorizationHeader, String requestId) {
        try {
            Result<AuthUserResponse> result = authWebClient.post()
                                                           .uri(ApiConstants.PumpAuthService.API_VALIDATE)
                                                           .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                                                           .header(SecurityConstants.REQUEST_ID, requestId)
                                                           .retrieve()
                                                           .body(new ParameterizedTypeReference<>() {
                                                           });

            if (result == null || result.getData().isEmpty()) {
                throw new RuntimeException("Auth user not found");
            }

            return result.getData().get();
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new UnauthorizedException("Invalid token");
        }
    }
}
