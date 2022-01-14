package com.example.broker.stream.flashsale;

import com.example.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//@Configuration
public class FlashSaleVoteStream {

    @Bean
    public KStream<String, String> kstreamFlashSaleVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));

        var flashSaleVoteStream = builder.stream("t.commodity.flashsale-vote",
                Consumed.with(stringSerde, flashSaleVoteSerde))
                .transformValues(() -> new FlashSaleVoteValueTransformer(voteStart, voteEnd))
                .filter((key, value) -> value != null)
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));
        flashSaleVoteStream.print(Printed.<String, String>toSysOut().withLabel("FlashSalveVoteUserItem"));
        flashSaleVoteStream.to("t.commodity.flashsale-vote-user-item");

        KStream<String, Long> flashSaleVoteResultStream = builder.table("t.commodity.flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem))
                .count()
                .toStream();
        flashSaleVoteResultStream.print(Printed.<String, Long>toSysOut().withLabel("FlashSalveVoteResult"));
        flashSaleVoteResultStream.to("t.commodity.flashsale-vote-result", Produced.with(stringSerde, Serdes.Long()));

        return flashSaleVoteStream;
    }
}
