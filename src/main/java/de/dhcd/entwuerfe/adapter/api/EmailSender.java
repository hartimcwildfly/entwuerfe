package de.dhcd.entwuerfe.adapter.api;


import de.dhcd.entwuerfe.model.EmailDto;


public interface EmailSender {
    
    void sende(EmailDto emailDto);
    
}
