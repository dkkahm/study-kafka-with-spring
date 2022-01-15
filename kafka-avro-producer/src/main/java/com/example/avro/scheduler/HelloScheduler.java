package com.example.avro.scheduler;

import com.example.avro.data.Hello;
import com.example.avro.producer.HelloProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class HelloScheduler {

    @Autowired
    private HelloProducer producer;

    @Scheduled(fixedRate = 1000)
    public void publishSchedule() {
        var data = Hello.newBuilder()
                .setMyIntField(ThreadLocalRandom.current().nextInt())
                .setMyStringField("Now is " + LocalTime.now())
                .build();

        producer.send(data);
    }
}
