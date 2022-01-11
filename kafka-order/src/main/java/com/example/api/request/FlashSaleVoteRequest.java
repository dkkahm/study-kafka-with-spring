package com.example.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlashSaleVoteRequest {
    private String customerId;
    private String itemName;
}
