package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackMessage {
    private String location;
    private String feedback;
    private int rating;
}
