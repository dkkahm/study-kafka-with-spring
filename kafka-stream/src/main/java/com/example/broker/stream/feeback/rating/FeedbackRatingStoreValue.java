package com.example.broker.stream.feeback.rating;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
public class FeedbackRatingStoreValue {
    private Map<Integer, Long> ratingMap = new TreeMap<>();
}
