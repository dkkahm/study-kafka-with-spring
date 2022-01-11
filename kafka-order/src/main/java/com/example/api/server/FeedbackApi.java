package com.example.api.server;

import com.example.api.request.FeedbackRequest;
import com.example.api.request.OrderRequest;
import com.example.api.response.OrderResponse;
import com.example.command.service.FeedbackService;
import com.example.command.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackApi {

    @Autowired
    private FeedbackService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody FeedbackRequest request) {
        service.publishFeedback(request);

        return ResponseEntity.ok().build();
    }
}
