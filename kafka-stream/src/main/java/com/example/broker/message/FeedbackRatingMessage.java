package com.example.broker.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@Data
@NoArgsConstructor
public class FeedbackRatingMessage {
    private String location;
    private double averageRating;
    private Map<Integer, Long> ratingMap = new TreeMap<>();
}
