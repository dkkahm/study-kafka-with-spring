package com.example.command.service;

import com.example.api.request.FeedbackRequest;
import com.example.api.request.PromotionRequest;
import com.example.command.action.FeedbackAction;
import com.example.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackAction action;

    public void publishFeedback(FeedbackRequest request) {
        action.publishToKafka(request);
    }
}
