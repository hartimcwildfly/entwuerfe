package de.dhcd.entwuerfe.adapter.inmemory;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import de.dhcd.entwuerfe.adapter.api.EmailSender;
import de.dhcd.entwuerfe.model.EmailDto;


@Component
@ApplicationScope
@Profile("!email")
public class EmailSenderInMemory implements EmailSender {
    
    @Override
    public void sende(EmailDto emailDto) {
    
    }
}
