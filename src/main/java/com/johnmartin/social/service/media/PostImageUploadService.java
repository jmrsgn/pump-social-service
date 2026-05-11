package com.johnmartin.social.service.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.johnmartin.social.constants.error.ValidationErrorConstants;
import com.johnmartin.social.utilities.LoggerUtility;

@Service
public class PostImageUploadService implements UploadService {

    private static final Class<PostImageUploadService> clazz = PostImageUploadService.class;

    private static final String POST_UPLOAD_DIRECTORY = "uploads/posts";

    @Override
    public String upload(MultipartFile file) {
        LoggerUtility.d(clazz, "Execute method: [upload]");

        if (file == null || file.isEmpty()) {
            throw new RuntimeException(ValidationErrorConstants.FILE_IS_EMPTY);
        }

        try {
            // Create upload directory if not exists
            Path uploadPath = Paths.get(POST_UPLOAD_DIRECTORY);
            LoggerUtility.d(clazz, String.format("uploadPath: [%s]", uploadPath.getFileName()));

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            LoggerUtility.d(clazz, String.format("originalFilename: [%s]", originalFilename));

            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String generatedFileName = UUID.randomUUID() + extension;
            LoggerUtility.d(clazz, String.format("generatedFileName: [%s]", generatedFileName));

            Path filePath = uploadPath.resolve(generatedFileName);
            LoggerUtility.d(clazz, String.format("filePath: [%s]", filePath.getFileName()));

            // Save file
            LoggerUtility.d(clazz, "Saving file");
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return relative URL/path
            return "/" + POST_UPLOAD_DIRECTORY + "/" + generatedFileName;
        } catch (IOException e) {
            throw new RuntimeException(ValidationErrorConstants.FAILED_TO_UPLOAD_FILE, e);
        }
    }
}
