package com.example.avro.tutorial;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecordBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class HelloAvroGeneric {
    public static void main(String[] args) {
        var schemaString = "{\n" +
                "    \"type\": \"record\",\n" +
                "    \"namespace\": \"com.example.avro.data\",\n" +
                "    \"name\": \"Hello\",\n" +
                "    \"doc\": \"Hello world avro\",\n" +
                "    \"fields\": [\n" +
                "        {\n" +
                "            \"name\": \"myStringField\",\n" +
                "            \"type\": \"string\",\n" +
                "            \"doc\": \"Just a string\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"myIntField\",\n" +
                "            \"type\": \"int\",\n" +
                "            \"doc\": \"Just a int\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        var schema = new Schema.Parser().parse(schemaString);

        var dataBuilder = new GenericRecordBuilder(schema);
        dataBuilder.set("myStringField", "Now is " + LocalTime.now());
        dataBuilder.set("myIntField", ThreadLocalRandom.current().nextInt());

        var data = dataBuilder.build();

        var datumWriter = new GenericDatumWriter<>();

        try (var dataWriter = new DataFileWriter<>(datumWriter)) {
            var file = new File("helloAvroGeneric.avro");
            dataWriter.create(data.getSchema(), file);
            dataWriter.append(data);

            System.out.println("Written : " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
