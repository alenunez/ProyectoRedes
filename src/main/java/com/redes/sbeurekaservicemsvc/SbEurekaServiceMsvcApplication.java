package com.redes.sbeurekaservicemsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SbEurekaServiceMsvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbEurekaServiceMsvcApplication.class, args);
    }

}
