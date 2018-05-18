package com.biroas.poc.file.search.worker.jms;

import com.biroas.poc.file.search.api.model.file.File;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;

@Component
public class FileIndexSender {

    @Inject
    private JmsTemplate jmsTemplate;

    private Destination destination=new ActiveMQQueue("file-index");

    public void sendFileIndexRequest(File file){
        jmsTemplate.convertAndSend(destination,file);
    }

}
