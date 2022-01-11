package com.example.broker.stream.commodity;

import com.example.broker.message.OrderMessage;
import com.example.broker.message.OrderPatternMessage;
import com.example.broker.message.OrderRewardMessage;
import com.example.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class CommodityTwoStream {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        KStream<String, OrderMessage> maskedOrderStream = builder.stream("t.commodity.order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        // 1st sink stream to pattern
        // summarize order item (total = price * quantity)
        final var branchProducer = Produced.with(stringSerde, orderPatternSerde);
        new KafkaStreamBrancher<String, OrderPatternMessage>()
                .branch(CommodityStreamUtil.isPlastic(), kstream -> kstream.to("t.commodity.pattern-two.plastic", branchProducer))
                .defaultBranch(kstream -> kstream.to("t.commodity.pattern-two.notplastic", branchProducer))
                .onTopOf(maskedOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

//        KStream<String, OrderPatternMessage>[] patternStream = maskedOrderStream.mapValues(CommodityStreamUtil::mapToOrderPattern)
//                        .branch(CommodityStreamUtil.isPlastic(), (k, v) -> true);

//        int plasticIndex = 0;
//        int notPlasticIndex = 1;
//
//        patternStream[plasticIndex].to("t.commodity.pattern-two.plastic", Produced.with(stringSerde, orderPatternSerde));
//        patternStream[notPlasticIndex].to("t.commodity.pattern-two.notplastic", Produced.with(stringSerde, orderPatternSerde));

        // 2nd sink stream to reward
        // filter only large quantity
        KStream<String, OrderRewardMessage> rewardStream = maskedOrderStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isChip())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey());
        rewardStream.to("t.commodity.reward-two", Produced.with(stringSerde, orderRewardSerde));

        // 3rd sink stream to storage
        KStream<String, OrderMessage> storageStream = maskedOrderStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t.commodity.storage-two", Produced.with(stringSerde, orderSerde));

        return maskedOrderStream;
    }
}
