package com.example.command.service;

import com.example.api.request.InventoryRequest;
import com.example.api.request.WebColorVoteRequest;
import com.example.api.request.WebLayoutVoteRequest;
import com.example.command.action.InventoryAction;
import com.example.command.action.WebVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebVoteService {
    @Autowired
    private WebVoteAction action;

    public void publishColorVote(WebColorVoteRequest request) {
        action.publishColorVote(request);
    }

    public void publishLayoutVote(WebLayoutVoteRequest request) {
        action.publishLayoutVote(request);
    }
}
