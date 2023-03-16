package com.sombra.edu.springmenteeproject;

import com.sombra.edu.springmenteeproject.repository.WalletRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMenteeProjectApplication {

    public static void main(String[] args) {
        WalletRepository.initData();
        SpringApplication.run(SpringMenteeProjectApplication.class, args);
    }

}
