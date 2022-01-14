package com.example.command.service;

import com.example.api.request.OnlineOrderRequest;
import com.example.api.request.OnlinePaymentRequest;
import com.example.command.action.OnlineOrderAction;
import com.example.command.action.OnlinePaymentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {
    @Autowired
    private OnlinePaymentAction action;

    public void publish(OnlinePaymentRequest request) {
        action.publish(request);
    }
}
