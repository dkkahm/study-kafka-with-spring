package com.example.command.action;

import com.example.api.request.OnlineOrderRequest;
import com.example.api.request.WebColorVoteRequest;
import com.example.api.request.WebLayoutVoteRequest;
import com.example.broker.message.OnlineOrderMessage;
import com.example.broker.message.WebColorVoteMessage;
import com.example.broker.message.WebLayoutVoteMessage;
import com.example.broker.producer.OnlineOrderProducer;
import com.example.broker.producer.WebColorVoteProducer;
import com.example.broker.producer.WebLayoutVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebVoteAction {
    @Autowired
    private WebColorVoteProducer webColorVoteProducer;

    @Autowired
    private WebLayoutVoteProducer webLayoutVoteProducer;

    public void publishColorVote(WebColorVoteRequest request) {
        var message = new WebColorVoteMessage(request);
        webColorVoteProducer.publish(message);
    }

    public void publishLayoutVote(WebLayoutVoteRequest request) {
        var message = new WebLayoutVoteMessage(request);
        webLayoutVoteProducer.publish(message);
    }
}
