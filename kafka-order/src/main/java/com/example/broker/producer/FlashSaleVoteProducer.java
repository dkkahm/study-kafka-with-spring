package com.example.broker.producer;

import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.FlashSaleVoteMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlashSaleVoteProducer {

    @Autowired
    private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

    public void publish(FlashSaleVoteMessage message) {
        kafkaTemplate.send("t.commodity.flashsale-vote", message);
    }
}
