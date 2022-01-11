package com.example.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackRequest {
    private String location;
    private String feedback;
    private int rating;
}
