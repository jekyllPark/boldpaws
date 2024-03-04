package com.dangdang.boldpaws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoldPawsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoldPawsApplication.class, args);
    }

}
