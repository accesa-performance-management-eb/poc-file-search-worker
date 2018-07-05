package com.biroas.poc.file.search.worker.service.email;

import com.biroas.poc.file.search.api.model.file.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class FileEmailSender {

    @Inject
    public JavaMailSender emailSender;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.to}")
    private String to;

    public void sendFileOnEmail(File file) throws MessagingException{
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject("New File Received: "+file.getFileName());
        helper.setFrom(from);
        helper.setText("You have received a new file: "+file.toString());

        FileSystemResource fileToSend = new FileSystemResource(new java.io.File(file.getParentDirectory()+ java.io.File.separator+file.getFileName()));
        helper.addAttachment(file.getFileName(), fileToSend);

        emailSender.send(mimeMessage);
    }
}
