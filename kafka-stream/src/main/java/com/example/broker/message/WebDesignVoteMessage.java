package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebDesignVoteMessage {
    private String username;
    private String color;
    private String layout;
}
