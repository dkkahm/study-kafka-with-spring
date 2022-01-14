package com.example.api.server;

import com.example.api.request.InventoryRequest;
import com.example.api.request.WebColorVoteRequest;
import com.example.api.request.WebLayoutVoteRequest;
import com.example.command.service.InventoryService;
import com.example.command.service.WebVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webvote")
public class WebVoteApi {

    @Autowired
    private WebVoteService service;

    @PostMapping(value = "/color", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> colorVote(@RequestBody WebColorVoteRequest request) {
        service.publishColorVote(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/layout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@RequestBody WebLayoutVoteRequest request) {
        service.publishLayoutVote(request);

        return ResponseEntity.ok().build();
    }
}
