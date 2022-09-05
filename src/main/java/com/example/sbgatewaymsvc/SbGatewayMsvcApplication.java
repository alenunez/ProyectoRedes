package com.example.sbgatewaymsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SbGatewayMsvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbGatewayMsvcApplication.class, args);
    }

}
