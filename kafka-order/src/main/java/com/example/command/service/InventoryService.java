package com.example.command.service;

import com.example.api.request.InventoryRequest;
import com.example.command.action.InventoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryAction action;

    public void addInventory(InventoryRequest request) {
        action.addInventory(request);
    }

    public void removeInventory(InventoryRequest request) {
        action.removeInventory(request);
    }
}
