package com.rantomah.boilerplate.core.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class EmailHelper {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent)
            throws MessagingException {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            emailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email", e);
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String text, File attachment)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(attachment);
        helper.addAttachment(file.getFilename(), file);

        emailSender.send(helper.getMimeMessage());
    }

    public void sendTemplatedEmail(
            String to, String subject, String templateName, Map<String, Object> templateModel)
            throws MessagingException {
        // Keep this method synchronous for reusability; callers that must not block should wrap it
        // in a Future
        Context context = new Context();
        context.setVariables(templateModel);

        String htmlContent = templateEngine.process(templateName, context);
        sendHtmlEmail(to, subject, htmlContent);
    }
}
