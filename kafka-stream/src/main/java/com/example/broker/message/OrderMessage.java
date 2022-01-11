package com.example.broker.message;

import com.example.util.LocalDateTimeDeserializer;
import com.example.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
    private String creditCardNumber;
    private String itemName;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderDateTime;
    private String orderLocation;
    private String orderNumber;
    private int price;
    private int quantity;

    public OrderMessage copy() {
        var copy = new OrderMessage();

        copy.setCreditCardNumber(this.creditCardNumber);
        copy.setItemName(this.getItemName());
        copy.setOrderDateTime(this.getOrderDateTime());
        copy.setOrderLocation(this.getOrderLocation());
        copy.setOrderNumber(this.getOrderNumber());
        copy.setPrice(this.getPrice());
        copy.setQuantity(this.getQuantity());

        return copy;
    }
}
