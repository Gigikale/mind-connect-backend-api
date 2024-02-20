package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class EmailImplementation implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @SneakyThrows
    @Override
    public void sendEmail(String message, String subject, String recipient) {
        log.info("Mail setup");
        log.info(recipient);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject(subject);
        messageHelper.setTo(recipient.trim());
        messageHelper.setText(message, true);
        messageHelper.setSentDate(new Date(System.currentTimeMillis()));

        mailSender.send(mimeMessage);

        log.info("Mail sent");
    }
}
