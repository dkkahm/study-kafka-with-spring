package com.example.broker.stream.feeback.rating;

import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.FeedbackRatingMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class FeedbackRatingStream {

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedbackRating(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        var feedbackRatingSerde = new JsonSerde<>(FeedbackRatingMessage.class);
        var feedbackRatingStoreValueSerde = new JsonSerde<>(FeedbackRatingStoreValue.class);

        var feedbackStream = builder.stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));

        var feedbackRatingStatStoreName = "feedbackRatingStatStore";
        var storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStatStoreName);
        var storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingStoreValueSerde);
        builder.addStateStore(storeBuilder);

        var feedbackRatingStream = feedbackStream.transformValues(() -> new FeedbackTransformer(feedbackRatingStatStoreName), feedbackRatingStatStoreName);
        feedbackRatingStream.print(Printed.<String, FeedbackRatingMessage>toSysOut().withLabel("FeedbackRating"));

        feedbackRatingStream.to("t.commodity.feedback.rating", Produced.with(stringSerde, feedbackRatingSerde));

        return feedbackStream;
    }
}
