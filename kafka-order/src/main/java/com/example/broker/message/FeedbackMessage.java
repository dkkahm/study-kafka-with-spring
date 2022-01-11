package com.example.broker.message;

import com.example.api.request.FeedbackRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackMessage {
    private String location;
    private String feedback;
    private int rating;

    public FeedbackMessage(FeedbackRequest request) {
        this.location = request.getLocation();
        this.feedback = request.getFeedback();
        this.rating = request.getRating();
    }
}
