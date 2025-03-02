package org.example.subapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubAppApplication {

  private static final Logger logger = LoggerFactory.getLogger(SubAppApplication.class);

  public static void main(String[] args) {
    logger.info("Starting SubscriptionsApplication...");
    SpringApplication.run(SubAppApplication.class, args);
    logger.info("SubscriptionsApplication started successfully.");
  }
}
