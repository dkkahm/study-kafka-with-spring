package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountMessage {
    private String discountCode;
    private int discountPercentage;
}
