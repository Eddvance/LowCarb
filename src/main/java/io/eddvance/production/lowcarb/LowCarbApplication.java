package io.eddvance.production.lowcarb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LowCarbApplication {

    public static void main(String[] args) {
        SpringApplication.run(LowCarbApplication.class, args);
    }

}