package com.example.command.service;

import com.example.api.request.DiscountRequest;
import com.example.api.request.PromotionRequest;
import com.example.command.action.DiscountAction;
import com.example.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    @Autowired
    private DiscountAction action;

    public void createDiscount(DiscountRequest request) {
        action.publishToKafka(request);
    }
}
