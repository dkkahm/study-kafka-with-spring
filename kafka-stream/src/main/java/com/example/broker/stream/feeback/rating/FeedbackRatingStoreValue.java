package com.example.broker.stream.feeback.rating;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackRatingStoreValue {
    private long countRating;
    private long sumRating;
}
