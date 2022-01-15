package com.example.avro.tutorial;

import com.example.avro.data.Hello;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class HelloAvro {
    public static void main(String[] args) {
//        var data = new Hello();
//        data.setMyIntField(ThreadLocalRandom.current().nextInt());
//        data.setMyStringField("This time is " + LocalTime.now());

        var data = Hello.newBuilder()
                .setMyIntField(ThreadLocalRandom.current().nextInt())
                .setMyStringField("This time is " + LocalTime.now())
                .build();

        var datumWriter = new SpecificDatumWriter<>(Hello.class);

        try (var dataWriter = new DataFileWriter<>(datumWriter)) {
            var file = new File("helloAvro.avro");
            dataWriter.create(data.getSchema(), file);
            dataWriter.append(data);

            System.out.println("Written : " + data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
