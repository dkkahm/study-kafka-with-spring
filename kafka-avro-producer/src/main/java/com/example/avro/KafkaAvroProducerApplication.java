package com.example.avro;

import com.example.avro.data.Avro01;
import com.example.avro.producer.Avro01Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class KafkaAvroProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaAvroProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
