package com.example.studyspringkafkaconsumer.consumer;

import com.example.studyspringkafkaconsumer.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class EmployeeJsonConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

//    @KafkaListener(topics = "t_employee")
//    public void consume(String message) throws JsonProcessingException {
//        var emp = objectMapper.readValue(message, Employee.class);
//        log.info("Employee is {}", emp);
//    }

    // ConsumerRecord<String, String> message
    @KafkaListener(topics = "t_employee")
    public void consume(ConsumerRecord<String, String> message) throws JsonProcessingException {
        var emp = objectMapper.readValue(message.value(), Employee.class);
        log.info("Employee is {}", emp);
    }
}
