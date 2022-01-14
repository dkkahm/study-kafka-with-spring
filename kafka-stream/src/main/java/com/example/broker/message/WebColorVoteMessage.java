package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebColorVoteMessage {
    private String username;
    private String color;
}
