package com.example.avro;

import com.example.avro.data.Avro01;
import com.example.avro.producer.Avro01Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class KafkaAvroProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaAvroProducerApplication.class, args);
	}

	@Autowired
	Avro01Producer producer;

	@Override
	public void run(String... args) throws Exception {
		var data = Avro01.newBuilder()
				.setActive(true)
				.setFullName("Full name " + ThreadLocalRandom.current().nextInt())
				.setMaritalStatus("SINGLE").build();

		producer.send(data);
	}
}
