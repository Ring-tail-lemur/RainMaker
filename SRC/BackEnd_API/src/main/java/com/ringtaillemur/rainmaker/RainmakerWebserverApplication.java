package com.ringtaillemur.rainmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RainmakerWebserverApplication {

  //test1
  public static void main(String[] args) {
    SpringApplication.run(RainmakerWebserverApplication.class, args);
  }
}
