package com.biroas.poc.file.search.worker.jms;

import com.biroas.poc.file.search.api.model.file.File;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;

@Component
public class FileIndexRequestSender {

    @Inject
    private JmsTemplate jmsTemplate;
    @Value("${activemq.queue.name}")
    private String destinationName;

    public void sendFileIndexRequest(File file) {
        Destination destination = new ActiveMQQueue(destinationName);
        jmsTemplate.convertAndSend(destination, file);
    }

}
