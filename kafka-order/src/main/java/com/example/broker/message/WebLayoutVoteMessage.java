package com.example.broker.message;

import com.example.api.request.WebColorVoteRequest;
import com.example.api.request.WebLayoutVoteRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebLayoutVoteMessage {
    private String username;
    private String layout;

    public WebLayoutVoteMessage(WebLayoutVoteRequest request) {
        this.username = request.getUsername();
        this.layout = request.getLayout();
    }
}
