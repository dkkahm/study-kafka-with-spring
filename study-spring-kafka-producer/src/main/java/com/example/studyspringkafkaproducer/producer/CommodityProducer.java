package com.example.studyspringkafkaproducer.producer;

import com.example.studyspringkafkaproducer.entity.Commodity;
import com.example.studyspringkafkaproducer.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommodityProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Commodity commodity) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(commodity);
        kafkaTemplate.send("t_commodity", commodity.getName(), json);
    }
}
