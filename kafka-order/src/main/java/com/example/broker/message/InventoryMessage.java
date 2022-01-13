package com.example.broker.message;

import com.example.api.request.FeedbackRequest;
import com.example.api.request.InventoryRequest;
import com.example.util.LocalDateTimeDeserializer;
import com.example.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InventoryMessage {
    private String location;
    private String item;
    private long quantity;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime transactionTime;

    public InventoryMessage(InventoryRequest request) {
        this.location = request.getLocation();
        this.item = request.getItem();
        this.quantity = request.getQuantity();
        this.transactionTime = request.getTransactionTime();
    }
}
