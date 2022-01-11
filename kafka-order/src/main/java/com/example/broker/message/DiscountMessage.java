package com.example.broker.message;

import com.example.api.request.DiscountRequest;
import com.example.api.request.PromotionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountMessage {
    private String discountCode;
    private int discountPercentage;

    public DiscountMessage(DiscountRequest request) {
        discountCode = request.getDiscountCode();
        discountPercentage = request.getDiscountPercentage();
    }
}
