package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PromotionMessage {
    private String promotionCode;
}
