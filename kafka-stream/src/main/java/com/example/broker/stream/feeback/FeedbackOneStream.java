package com.example.broker.stream.feeback;

import com.example.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FeedbackOneStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    @Bean
    public KStream<String, String> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

//        var goodFeedbackSteam = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde))
//                .flatMapValues(mapperGoodWords());
        var feedbackSteam = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));
        feedbackSteam.print(Printed.<String, FeedbackMessage>toSysOut().withLabel("Feedback"));

        var goodFeedbackSteam = feedbackSteam.flatMap((k, v) -> {
            return Arrays.asList(v.getFeedback().replaceAll("[^a-zA-Z]", " ").toLowerCase().split("\\s+")).stream()
                    .filter(word -> GOOD_WORDS.contains(word))
                    .distinct()
                    .map(goodWord -> KeyValue.pair(v.getLocation(), goodWord))
                    .collect(Collectors.toList());
                }
            );

        goodFeedbackSteam.print(Printed.<String, String>toSysOut().withLabel("Good Feedback"));

        goodFeedbackSteam.to("t.commodity.feedback-good");

        return goodFeedbackSteam;
    }

    private ValueMapper<FeedbackMessage, Iterable<String>> mapperGoodWords() {
        return feedbackMessage -> Arrays.asList(
                feedbackMessage.getFeedback()
                        .replace("[^a-zA-A]", " ").toLowerCase()
                        .split("\\s+")).stream()
                .filter(word -> GOOD_WORDS.contains(word))
                .distinct()
                .collect(Collectors.toList());
    }
}
