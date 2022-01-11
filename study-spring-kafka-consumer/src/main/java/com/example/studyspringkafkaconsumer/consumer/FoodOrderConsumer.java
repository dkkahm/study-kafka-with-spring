package com.example.studyspringkafkaconsumer.consumer;

import com.example.studyspringkafkaconsumer.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(FoodOrderConsumer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final int MAX_AMOUNT_ORDER = 7;

//    @KafkaListener(topics = "t_food_order", errorHandler = "myFoodOrderErrorHandler")
//    @KafkaListener(topics = "t_food_order")
//    @KafkaListener(topics = "t_food_order", containerFactory = "foodOrderRetryContainerFactory")
    @KafkaListener(topics = "t_food_order", containerFactory = "foodOrderDeadLetterRecoveryContainerFactory")
    public void consume(String message) throws JsonProcessingException {
        var foodOrder = objectMapper.readValue(message, FoodOrder.class);

        if(foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
            throw new IllegalArgumentException("Food order amount is too many");
        }

        log.info("Food order valid: {}", foodOrder);
    }
}
