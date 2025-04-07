package tn.esprit.ms.msreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReservationApplication.class, args);
    }

}
