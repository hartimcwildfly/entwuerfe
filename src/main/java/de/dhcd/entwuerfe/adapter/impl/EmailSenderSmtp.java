package de.dhcd.entwuerfe.adapter.impl;


import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import de.dhcd.entwuerfe.adapter.api.EmailSender;
import de.dhcd.entwuerfe.model.EmailDto;


@Component
@ApplicationScope
@Profile("email")
public class EmailSenderSmtp implements EmailSender {
    
    private final MailSender mailSender;
    
    public EmailSenderSmtp(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @Override
    public void sende(EmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom(emailDto.getFrom());
        mailMessage.setTo(emailDto.getTo());
        mailMessage.setSubject(emailDto.getSubject());
        mailMessage.setText(emailDto.getText());
        
        mailSender.send(mailMessage);
    }
}
