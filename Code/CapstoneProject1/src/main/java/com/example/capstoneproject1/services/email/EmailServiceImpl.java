package com.example.capstoneproject1.services.email;


import com.example.capstoneproject1.dto.request.ContactForm;
import com.example.capstoneproject1.services.thymeleaf.ThymeleafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ThymeleafService thymeleafService;

    @Value("${spring.mail.username}")
    private String email;



    @Override
    public void sendMailCreateCustomer(ContactForm contactForm) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(email);
            helper.setSubject("Contact Shared Space Finder");

            Map<String, Object> variables = new HashMap<>();
            variables.put("first_name", contactForm.getFirstName());
            variables.put("last_name", contactForm.getLastName());
            variables.put("email", contactForm.getEmail());
            variables.put("title", contactForm.getTitle());
            variables.put("content", contactForm.getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            variables.put("date", sdf.format(new Date()));

            // use thymeleaf to make template email
            String emailContent = thymeleafService.createContent("Contact.html", variables);

            helper.setText(emailContent, true);
            helper.setFrom(contactForm.getEmail());

            mailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
