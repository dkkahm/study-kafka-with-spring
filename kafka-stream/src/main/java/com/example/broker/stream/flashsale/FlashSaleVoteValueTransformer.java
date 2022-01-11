package com.example.broker.stream.flashsale;

import com.example.broker.message.FlashSaleVoteMessage;
import com.example.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

public class FlashSaleVoteValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {
    private final long voteStartTime;
    private final long voteEndTime;
    private ProcessorContext processorContext;

    public FlashSaleVoteValueTransformer(LocalDateTime voteStart, LocalDateTime voteEnd) {
        this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(voteStart);
        this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(voteEnd);
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        var recordTime = processorContext.timestamp();

        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }

    @Override
    public void close() {

    }
}
