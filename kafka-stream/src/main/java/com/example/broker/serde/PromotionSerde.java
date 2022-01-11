package com.example.broker.serde;

import com.example.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {

    public PromotionSerde() {
        super(new CustomJsonSerializer<>(), new CustomJsonDeserializer<>(PromotionMessage.class));
    }
}
