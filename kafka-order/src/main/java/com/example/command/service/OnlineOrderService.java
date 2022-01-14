package com.example.command.service;

import com.example.api.request.InventoryRequest;
import com.example.api.request.OnlineOrderRequest;
import com.example.command.action.InventoryAction;
import com.example.command.action.OnlineOrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderService {
    @Autowired
    private OnlineOrderAction action;

    public void publish(OnlineOrderRequest request) {
        action.publish(request);
    }
}
