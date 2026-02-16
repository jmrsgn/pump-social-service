package com.johnmartin.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PumpSocialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PumpSocialServiceApplication.class, args);
    }

}
