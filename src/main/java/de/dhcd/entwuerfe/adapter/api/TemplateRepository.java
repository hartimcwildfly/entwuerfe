package de.dhcd.entwuerfe.adapter.api;


import de.dhcd.entwuerfe.model.Message;


public interface TemplateRepository {
    
    String getSubject(Message message);
    
    String getBody(Message message);
}
