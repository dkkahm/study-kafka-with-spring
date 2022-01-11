package com.example.studyspringkafkaproducer;

import com.example.studyspringkafkaproducer.entity.Employee;
import com.example.studyspringkafkaproducer.entity.FoodOrder;
import com.example.studyspringkafkaproducer.producer.EmployeeJsonProducer;
import com.example.studyspringkafkaproducer.producer.FoodOrderProducer;
import com.example.studyspringkafkaproducer.producer.HelloKafkaProducer;
import com.example.studyspringkafkaproducer.producer.KafkaKeyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
//@EnableScheduling
public class StudySpringKafkaProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringKafkaProducerApplication.class, args);
    }

    @Autowired
    private FoodOrderProducer producer;

    @Override
    public void run(String... args) throws Exception {
        var chickenOrder = new FoodOrder(3, "Chicken");
        var fishOrder = new FoodOrder(10, "Fish");
        var pizzaOrder = new FoodOrder(5, "Pizza");

        producer.send(chickenOrder);
        producer.send(fishOrder);
        producer.send(pizzaOrder);
    }
}
