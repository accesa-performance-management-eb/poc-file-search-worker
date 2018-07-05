package com.biroas.poc.file.search.worker.jms;

import com.biroas.poc.file.search.api.model.file.File;
import com.biroas.poc.file.search.worker.service.email.FileEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import javax.inject.Inject;


public class FileDownloadRequestReceiver {

    private final Logger logger = LoggerFactory.getLogger(FileDownloadRequestReceiver.class);

    @Inject
    FileEmailSender fileEmailSender;

    @JmsListener(destination = "#{@DynamicDestination.destinationName}")
    public void receiveFileIndexRequest(File file) throws Exception {
        logger.info("Received file download request: {}", file);
        fileEmailSender.sendFileOnEmail(file);
    }
}
