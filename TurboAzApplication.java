package org.example.turboaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"org.example"})
@EnableJpaRepositories(basePackages = {"org.example"})
@ComponentScan(basePackages = {"org.example"})
@SpringBootApplication
public class TurboAzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurboAzApplication.class, args);
    }

}
