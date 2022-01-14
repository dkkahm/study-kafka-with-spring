package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebLayoutVoteMessage {
    private String username;
    private String layout;
}
