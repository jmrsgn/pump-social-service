package com.johnmartin.social.constants.api;

public final class ExternalServiceConstants {

    public static final String API_BASE_V1_INTERNAL = "/api/v1/internal";

    private ExternalServiceConstants() {
    }

    public static final class OpenAI {

        private OpenAI() {
        }

        public static final String BASE_URL = "https://api.openai.com";
        public static final String CHAT_COMPLETIONS = "/v1/chat/completions";
        public static final String MODERATIONS = "/v1/moderations";
    }

    public static final class PumpAuthService {

        private PumpAuthService() {
        }

        // public static final String BASE_URL =
        // "http://pump-auth-service:8080";

        public static final String BASE_URL = "http://localhost:8081";
        public static final String API_VALIDATE = API_BASE_V1_INTERNAL + "/api/v1/internal/auth/validate";
    }
}
