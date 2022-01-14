package com.example.broker.message;

import com.example.api.request.OnlineOrderRequest;
import com.example.util.LocalDateTimeDeserializer;
import com.example.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OnlineOrderMessage {
    private String onlineOrderNumber;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderDateTime;

    private int totalAmount;
    private String username;

    public OnlineOrderMessage(OnlineOrderRequest request) {
        this.onlineOrderNumber = request.getOnlineOrderNumber();
        this.orderDateTime = request.getOrderDateTime();
        this.totalAmount = request.getTotalAmount();
        this.username = request.getUsername();
    }
}
