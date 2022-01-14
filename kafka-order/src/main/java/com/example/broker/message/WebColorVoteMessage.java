package com.example.broker.message;

import com.example.api.request.OnlineOrderRequest;
import com.example.api.request.WebColorVoteRequest;
import com.example.util.LocalDateTimeDeserializer;
import com.example.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WebColorVoteMessage {
    private String username;
    private String color;

    public WebColorVoteMessage(WebColorVoteRequest request) {
        this.username = request.getUsername();
        this.color = request.getColor();
    }
}
