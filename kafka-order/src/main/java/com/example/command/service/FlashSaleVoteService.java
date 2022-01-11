package com.example.command.service;

import com.example.api.request.FlashSaleVoteRequest;
import com.example.command.action.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashSaleVoteService {
    @Autowired
    private FlashSaleVoteAction action;

    public void publishFeedback(FlashSaleVoteRequest request) {
        action.publishToKafka(request);
    }
}
