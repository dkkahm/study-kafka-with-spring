package com.example.broker.stream.feeback.rating;

import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.FeedbackRatingMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingMessage> {
    private ProcessorContext processorContext;
    private final String stateStoreName;
    private KeyValueStore<String, FeedbackRatingStoreValue> ratingStateStore;

    public FeedbackTransformer(String stateStoreName) {
        if(StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("State store name must not empty");
        }

        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingStoreValue>)this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingMessage transform(FeedbackMessage feedbackMessage) {
        var storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                .orElse(new FeedbackRatingStoreValue());

        // update new store
        var newSumRating = storeValue.getSumRating() + feedbackMessage.getRating();
        storeValue.setSumRating(newSumRating);
        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        // put new store to state store
        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        // build branch rating
        var branchRating = new FeedbackRatingMessage();
        branchRating.setLocation(feedbackMessage.getLocation());
        double averageRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;
    }

    @Override
    public void close() {

    }
}
