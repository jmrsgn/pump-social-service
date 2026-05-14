package com.johnmartin.social.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.johnmartin.social.constants.api.ExternalServiceConstants;

@Configuration
public class OpenAiClientConfig {

    @Value("${openai.api-key}")
    private String apiKey;

    @Bean
    public RestClient openAiRestClient() {
        return RestClient.builder()
                         .baseUrl(ExternalServiceConstants.OpenAI.BASE_URL)
                         .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                         .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                         .build();
    }
}
