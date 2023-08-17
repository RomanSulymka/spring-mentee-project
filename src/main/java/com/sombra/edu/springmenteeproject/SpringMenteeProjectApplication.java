package com.sombra.edu.springmenteeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SpringMenteeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMenteeProjectApplication.class, args);
    }

}
