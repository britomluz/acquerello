package com.restaurant.acquerello.services.impl;

import ch.qos.logback.core.helpers.Transform;
import com.restaurant.acquerello.models.Order;
import com.restaurant.acquerello.models.User;
import com.restaurant.acquerello.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;


    public void sendMail(User user, Order order, String body){

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("acquerelloMindhub@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Acquerrello Restaurant");
        mailMessage.setText(body);

        javaMailSender.send(mailMessage);
    }

/*
    public void sendMailTo(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }*/
}
