package com.johnmartin.social.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.social.ai.dto.response.GenerateCaptionResponse;
import com.johnmartin.social.ai.service.CaptionGeneratorService;
import com.johnmartin.social.constants.api.ApiConstants;
import com.johnmartin.social.dto.response.common.Result;

@RestController
@RequestMapping(ApiConstants.Path.API_AI)
public class AiController {

    private final CaptionGeneratorService captionGeneratorService;

    public AiController(CaptionGeneratorService aiCaptionService) {
        this.captionGeneratorService = aiCaptionService;
    }

    @PostMapping(value = ApiConstants.Path.CAPTIONS)
    public ResponseEntity<Result<GenerateCaptionResponse>> generateCaption(@RequestParam(required = false) String prompt) {
        GenerateCaptionResponse response = captionGeneratorService.generateCaption(prompt);
        return ResponseEntity.ok(Result.success(response));
    }
}
