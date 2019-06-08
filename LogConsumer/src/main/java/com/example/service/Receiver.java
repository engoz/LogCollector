package com.example.service;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

public class Receiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${kafka.topic}",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                    }))
    //@KafkaListener(topics = "${kafka.topic}", groupId = "ConsumerGroup1")
    public void receive(String payload) {
        LOGGER.info("received payload='{}'", payload);
        latch.countDown();
    }
}
