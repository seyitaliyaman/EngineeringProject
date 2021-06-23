package com.safestagram.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UploadPostRequest {
    private String postDescription;
    private MultipartFile post;
}
