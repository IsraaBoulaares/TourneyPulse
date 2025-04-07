package tn.esprit.microservice.tournoii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TournoiiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournoiiApplication.class, args);
    }

}
