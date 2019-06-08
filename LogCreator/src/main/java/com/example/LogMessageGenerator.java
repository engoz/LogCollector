package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LogMessageGenerator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.application.name}")
    private String name;


    @Scheduled(fixedDelay = 1000L)
    void logSomeStuff(){

        int i = new Random().nextInt(100);


        if (i % 2 == 0){
            logger.info("Hello-from-{}",name,name);
        } else if (i % 3 == 0){
            logger.warn("Hello-from-{}",name);
        }else if (i % 5 == 0){
            logger.debug("Hello-from-{}",name);
        }else if (i % 7 == 0){
            logger.error("Hello-from-{}",name);
        }else {
            logger.info("Hello-from-{}",name);
        }

    }
}

