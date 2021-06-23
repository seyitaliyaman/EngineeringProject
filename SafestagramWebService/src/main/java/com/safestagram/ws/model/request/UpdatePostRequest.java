package com.safestagram.ws.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePostRequest {
    private String postDescription;
    private Float latitude;
    private Float longitude;
}
