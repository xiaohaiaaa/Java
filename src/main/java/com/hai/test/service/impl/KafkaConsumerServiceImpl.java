/*
package com.hai.test.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImpl {

    //@KafkaListener(topics = {"test"}, groupId = "group1", containerFactory = "kafkaListenerContainerFactory")
    public void kafkaListener(String message, Acknowledgment acknowledgment){
        System.out.println(message);
        // 触发提交offset偏移量
        acknowledgment.acknowledge();
    }
}
*/
