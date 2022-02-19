package de.dhcd.entwuerfe.adapter.impl;


import static io.vavr.API.$;
import static io.vavr.API.Case;

import de.dhcd.entwuerfe.adapter.api.TemplateRepository;
import de.dhcd.entwuerfe.model.DraftApprovedMessage;
import de.dhcd.entwuerfe.model.DraftDeclinedMessage;
import de.dhcd.entwuerfe.model.Message;
import io.vavr.API;


public class TemplateRepositoryFreemarker implements TemplateRepository {
    
    @Override
    public String getSubject(Message message) {
        return API.Match(message).of(
                Case(
                        $(it -> it instanceof DraftApprovedMessage),
                        msg -> "Enwturf von " + message.getKundenname() + " - " + message.getProjektname() + " wurde freigegeben"),
                Case(
                        $(it -> it instanceof DraftDeclinedMessage),
                        msg -> "Entwurf von " + message.getKundenname() + " - " + message.getProjektname() + " wurde abgelehnt")
        );
    }
    
    @Override
    public String getBody(Message message) {
        return null;
    }
}
