package com.suracki.residentmystery.email;

import com.suracki.residentmystery.controller.HomeController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 *
 * MailClient
 *
 * Used by UserService to send password reset emails
 *
 */
public class MailClient {

    private static final Logger logger = LogManager.getLogger(MailClient.class);

    @Value("${email.user.varname}")
    private String uservar;

    @Value("${email.pw.varname}")
    private String userpass;

    public String getUser() {
        return System.getenv(uservar);
    }

    public String getPassword() {
        return System.getenv(userpass);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(getUser());
        mailSender.setPassword(getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
