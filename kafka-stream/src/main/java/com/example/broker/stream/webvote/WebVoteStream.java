package com.example.broker.stream.webvote;

import com.example.broker.message.WebColorVoteMessage;
import com.example.broker.message.WebDesignVoteMessage;
import com.example.broker.message.WebLayoutVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class WebVoteStream {

    @Bean
    public KStream<String, WebDesignVoteMessage> kstreamDesignVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
        var layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
        var designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

        // color
        var colorStream = builder.stream("t.commodity.web.vote.color", Consumed.with(stringSerde, colorSerde))
                .mapValues(v -> v.getColor());
        colorStream.print(Printed.<String, String>toSysOut().withLabel("Color"));
        var colorTable = colorStream.toTable();

        // layout
        var layoutStream = builder.stream("t.commodity.web.vote.layout", Consumed.with(stringSerde, layoutSerde))
                .mapValues(v -> v.getLayout());
        layoutStream.print(Printed.<String, String>toSysOut().withLabel("Layout"));
        var layoutTable = layoutStream.toTable();

        // join
        var joinTable = colorTable.join(layoutTable, this::voteJoiner, Materialized.with(stringSerde, designSerde));
        joinTable.toStream().to("t.commodity.web.vote.result", Produced.with(stringSerde, designSerde));

        // vote result
        joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getColor(), votedDesign.getColor()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote - color"));

        joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getLayout(), votedDesign.getLayout()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote - layout"));

        return joinTable.toStream();
    }

    private WebDesignVoteMessage voteJoiner(String color, String layout) {
        var result = new WebDesignVoteMessage();

        result.setColor(color);
        result.setLayout(layout);

        return result;
    }
}
