package com.example.broker.stream.orderpayment;

import com.example.broker.message.OnlineOrderMessage;
import com.example.broker.message.OnlineOrderPaymentMessage;
import com.example.broker.message.OnlinePaymentMessage;
import com.example.util.OnlineOrderTimestampExtractor;
import com.example.util.OnlinePaymentTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
public class OrderPaymentStream {
    @Bean
    public KStream<String, OnlineOrderMessage> kstreamOrderPayment(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        var paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        var orderStream = builder.stream("t.commodity.online-order",
                Consumed.with(stringSerde, orderSerde, new OnlineOrderTimestampExtractor(), null));
        var paymentStream = builder.stream("t.commodity.online-payment",
                Consumed.with(stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));

        orderStream.join(paymentStream, this::joinOrderPayment, JoinWindows.of(Duration.ofMinutes(1)),
                StreamJoined.with(stringSerde, orderSerde, paymentSerde))
                .through("t.commodity.join-order-payment", Produced.with(stringSerde, orderPaymentSerde))
                .print(Printed.toSysOut());

        return orderStream;
    }

    private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage order, OnlinePaymentMessage payment) {
        var result = new OnlineOrderPaymentMessage();

        result.setOnlineOrderNumber(order.getOnlineOrderNumber());
        result.setOrderDateTime(order.getOrderDateTime());
        result.setTotalAmount(order.getTotalAmount());
        result.setUsername(order.getUsername());

        result.setPaymentDateTime(payment.getPaymentDateTime());
        result.setPaymentMethod(payment.getPaymentMethod());
        result.setPaymentNumber(payment.getPaymentNumber());

        return result;
    }
}
