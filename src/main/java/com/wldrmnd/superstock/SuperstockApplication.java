package com.wldrmnd.superstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SuperstockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperstockApplication.class, args);
    }

}
