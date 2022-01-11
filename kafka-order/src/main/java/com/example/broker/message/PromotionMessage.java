package com.example.broker.message;

import com.example.api.request.PromotionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromotionMessage {
    private String promotionCode;

    public PromotionMessage(PromotionRequest request) {
        promotionCode = request.getPromotionCode();
    }
}
