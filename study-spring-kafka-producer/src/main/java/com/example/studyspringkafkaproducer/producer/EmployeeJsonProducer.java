package com.example.studyspringkafkaproducer.producer;

import com.example.studyspringkafkaproducer.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeJsonProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(Employee emp) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(emp);
        kafkaTemplate.send("t_employee", json);
    }

    public void printProperties() {
        Map<String, Object> configurationProperties = kafkaTemplate.getProducerFactory().getConfigurationProperties();
        System.out.println(configurationProperties);
    }
}
