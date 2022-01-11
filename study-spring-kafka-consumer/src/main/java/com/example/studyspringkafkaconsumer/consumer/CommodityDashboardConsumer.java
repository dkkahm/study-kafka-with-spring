package com.example.studyspringkafkaconsumer.consumer;

import com.example.studyspringkafkaconsumer.entity.Commodity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// @Service
public class CommodityDashboardConsumer {
    private static final Logger log = LoggerFactory.getLogger(CommodityDashboardConsumer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "t_commodity", groupId = "cg-dashboard")
    public void consume(String message) throws JsonProcessingException {
        var commodity = objectMapper.readValue(message, Commodity.class);
        log.info("Dashboard logic for {}", commodity);
    }
}
