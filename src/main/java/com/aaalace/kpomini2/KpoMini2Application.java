package com.aaalace.kpomini2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KpoMini2Application {

    public static void main(String[] args) {
        SpringApplication.run(KpoMini2Application.class, args);
    }

}
