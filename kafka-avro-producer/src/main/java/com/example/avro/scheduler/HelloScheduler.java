package com.example.avro.scheduler;

import com.example.avro.data.Avro01;
import com.example.avro.producer.Avro01Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class Avro01Scheduler {

    @Autowired
    private Avro01Producer producer;

    @Scheduled(fixedRate = 1000)
    public void publishSchedule() {
        var data = Avro01.newBuilder()
                .setFullName("Fullname - " + ThreadLocalRandom.current().nextInt())
                .setMaritalStatus("SINGLE")
                .setActive(true)
                .build();

        producer.send(data);
    }
}
