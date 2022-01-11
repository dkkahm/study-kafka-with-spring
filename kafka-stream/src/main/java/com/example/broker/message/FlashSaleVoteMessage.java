package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlashSaleVoteMessage {
    private String customerId;
    private String itemName;
}
