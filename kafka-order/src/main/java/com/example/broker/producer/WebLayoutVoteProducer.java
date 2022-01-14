package com.example.broker.producer;

import com.example.broker.message.WebColorVoteMessage;
import com.example.broker.message.WebLayoutVoteMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebLayoutVoteProducer {

    @Autowired
    private KafkaTemplate<String, WebLayoutVoteMessage> kafkaTemplate;

    public void publish(WebLayoutVoteMessage message) {
        kafkaTemplate.send("t.commodity.web.vote.layout", message.getUsername(), message);
    }
}
