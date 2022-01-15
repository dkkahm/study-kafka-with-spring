package com.example.avro.message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HelloPositiveUppercase {
    private int positiveInt;
    private String uppercaseString;
}
