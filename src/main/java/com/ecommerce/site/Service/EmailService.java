package com.ecommerce.site.Service;

import com.ecommerce.site.Model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;


    @Value("spring.mail.username")
    private String fromEmail;

    public void sendOrderConfirmation(Order order){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Order Confirmation");
        message.setText("Your order has been confirmed. Order ID :"+order.getId());
        mailSender.send(message);
    }

}
