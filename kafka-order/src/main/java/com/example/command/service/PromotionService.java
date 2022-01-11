package com.example.command.service;

import com.example.api.request.PromotionRequest;
import com.example.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    @Autowired
    private PromotionAction action;

    public void createPromotion(PromotionRequest request) {
        action.publishToKafka(request);
    }
}
