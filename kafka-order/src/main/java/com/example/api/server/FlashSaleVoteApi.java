package com.example.api.server;

import com.example.api.request.FlashSaleVoteRequest;
import com.example.command.service.FeedbackService;
import com.example.command.service.FlashSaleVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flashsalevote")
public class FlashSaleVoteApi {

    @Autowired
    private FlashSaleVoteService service;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody FlashSaleVoteRequest request) {
        service.publishFeedback(request);

        return ResponseEntity.ok().build();
    }
}
