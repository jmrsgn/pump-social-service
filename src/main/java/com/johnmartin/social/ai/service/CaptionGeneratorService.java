package com.johnmartin.social.ai.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.johnmartin.social.ai.constants.CaptionPromptConstants;
import com.johnmartin.social.ai.dto.response.GenerateCaptionResponse;
import com.johnmartin.social.constants.api.ExternalServiceConstants;
import com.johnmartin.social.constants.error.ExternalServiceErrorConstants;
import com.johnmartin.social.service.AuthService;
import com.johnmartin.social.utilities.LoggerUtility;

@Service
public class CaptionGeneratorService {

    private static final Class<CaptionGeneratorService> clazz = CaptionGeneratorService.class;
    private static final String MODEL = "gpt-4.1-mini";
    private static final double TEMPERATURE = 0.8;
    private final AuthService authService;
    private final RestClient openAiRestClient;

    public CaptionGeneratorService(AuthService authService, RestClient openAiRestClient) {
        this.authService = authService;
        this.openAiRestClient = openAiRestClient;
    }

    public GenerateCaptionResponse generateCaption(String context) {
        LoggerUtility.d(clazz, "Execute method: [generateCaption]");

        try {
            // Validate authenticated user
            authService.getAuthUser();

            String userPrompt = buildUserPrompt(context);
            LoggerUtility.t(clazz, String.format("Generated userPrompt: [%s]", userPrompt));

            Map<String, Object> requestBody = buildRequestBody(userPrompt);

            Map<String, Object> response = openAiRestClient.post()
                                                           .uri(ExternalServiceConstants.OpenAI.CHAT_COMPLETIONS)
                                                           .body(requestBody)
                                                           .retrieve()
                                                           .body(Map.class);

            if (response == null || response.isEmpty()) {
                throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
            }

            String caption = extractCaption(response);

            if (StringUtils.isBlank(caption)) {
                throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
            }

            LoggerUtility.d(clazz, String.format("caption: [%s]", caption));
            return new GenerateCaptionResponse(caption);
        } catch (Exception e) {
            LoggerUtility.e(clazz, "Failed to generate caption", e);
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION, e);
        }
    }

    private String buildUserPrompt(String context) {
        return String.format(CaptionPromptConstants.USER_PROMPT, StringUtils.defaultString(context));
    }

    private Map<String, Object> buildRequestBody(String userPrompt) {
        return Map.of("model",
                      MODEL,
                      "messages",
                      List.of(Map.of("role", "system", "content", CaptionPromptConstants.SYSTEM_PROMPT),
                              Map.of("role", "user", "content", userPrompt)),
                      "temperature",
                      TEMPERATURE);
    }

    private String extractCaption(Map<String, Object> response) {
        Object choicesObject = response.get("choices");

        if (!(choicesObject instanceof List<?> choices) || CollectionUtils.isEmpty(choices)) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
        }

        Object firstChoiceObject = choices.get(0);

        if (!(firstChoiceObject instanceof Map<?, ?> firstChoice)) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
        }

        Object messageObject = firstChoice.get("message");

        if (!(messageObject instanceof Map<?, ?> message)) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
        }

        Object contentObject = message.get("content");

        if (!(contentObject instanceof String caption)) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_GENERATE_CAPTION);
        }

        return StringUtils.trim(caption);
    }
}
