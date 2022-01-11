package com.example.broker.stream.feeback;

import com.example.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FeedbackOneStream {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);

        var sourceStream = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));
        var feedbackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        var feedbackGoodCountStream = feedbackStreams[0].through("t.commodity.feedback-good").groupByKey().count().toStream();
        feedbackGoodCountStream.print(Printed.<String, Long>toSysOut().withLabel("Good"));
        feedbackGoodCountStream.to("t.commodity.feedback.good-count");

        KStream<String, Long> feedbackBadCountStream = feedbackStreams[1].through("t.commodity.feedback-bad").groupByKey().count().toStream();
        feedbackBadCountStream.print(Printed.<String, Long>toSysOut().withLabel("Bad"));
        feedbackBadCountStream.to("t.commodity.feedback.bad-count");

        return sourceStream;
    }

    private Predicate<? super String,? super String> isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    private Predicate<? super String,? super String> isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (k, v) -> Arrays.asList(v.getFeedback().replaceAll("[^a-zA-Z]", " ").split("\\s+")).stream()
                .map(String::toLowerCase)
                .distinct()
                .map(word -> KeyValue.pair(v.getLocation(), word))
                .collect(Collectors.toList());
    }
}
