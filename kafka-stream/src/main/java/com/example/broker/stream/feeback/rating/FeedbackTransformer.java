package com.example.broker.stream.feeback.rating;

import com.example.broker.message.FeedbackMessage;
import com.example.broker.message.FeedbackRatingMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Map;
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
        var ratingMap = storeValue.getRatingMap();

        var currentRatingCount = Optional.ofNullable(ratingMap.get(feedbackMessage.getRating())).orElse(0L);
        var newRatingCount = currentRatingCount + 1;
        ratingMap.put(feedbackMessage.getRating(), newRatingCount);
        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        // build branch rating
        var branchRating = new FeedbackRatingMessage();
        branchRating.setLocation(feedbackMessage.getLocation());
        branchRating.setRatingMap(ratingMap);
        branchRating.setAverageRating(calculateAverage(ratingMap));

        return branchRating;
    }

    private double calculateAverage(Map<Integer, Long> ratingMap) {
        var sumRating = 0L;
        var countRating = 0L;

        for(var entry : ratingMap.entrySet()) {
            sumRating += entry.getKey() * entry.getValue();
            countRating += entry.getValue();
        }

        return Math.round((double) sumRating / countRating * 10d) / 10d;
    }

    @Override
    public void close() {

    }
}
