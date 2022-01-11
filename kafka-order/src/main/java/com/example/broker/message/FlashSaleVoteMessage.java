package com.example.broker.message;

import com.example.api.request.FeedbackRequest;
import com.example.api.request.FlashSaleVoteRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlashSaleVoteMessage {
    private String customerId;
    private String itemName;

    public FlashSaleVoteMessage(FlashSaleVoteRequest request) {
        this.customerId = request.getCustomerId();
        this.itemName = request.getItemName();
    }
}
