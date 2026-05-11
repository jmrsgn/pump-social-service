package com.johnmartin.social.service.media;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    String upload(MultipartFile file);
}
