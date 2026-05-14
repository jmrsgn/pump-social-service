package com.johnmartin.social.ai.constants;

public final class CaptionPromptConstants {

    private CaptionPromptConstants() {
    }

    public static final String SYSTEM_PROMPT = """
            You are a fitness social media
            caption assistant.
            """;

    public static final String USER_PROMPT = """
            Generate a short motivational
            gym social media caption.

            Tone:
            motivational,
            aesthetic,
            fitness-focused.

            User context:
            %s
            """;
}
