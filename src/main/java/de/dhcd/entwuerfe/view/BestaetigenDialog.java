package de.dhcd.entwuerfe.view;


import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;

import de.dhcd.entwuerfe.adapter.api.EntwurfRepository;
import de.dhcd.entwuerfe.model.draft.Draft;


public class BestaetigenDialog extends Dialog {
    
    private final TextField firstnameField = new TextField("Vorname");
    private final TextField lastnameField  = new TextField("Nachname");
    private final Button    button         = new Button("Ablehnen");
    
    public BestaetigenDialog(EntwurfRepository entwurfRepository, Draft entwurf) {
        firstnameField.setRequired(true);
        lastnameField.setRequired(true);
        
        HasValue.ValueChangeListener valueChangeListener = valueChangeEvent -> button.setEnabled(isValid());
        
        firstnameField.addValueChangeListener(valueChangeListener);
        lastnameField.addValueChangeListener(valueChangeListener);
        
        button.addClickListener(clickEvent -> {
            Draft updatedEntwurf = entwurf.akzeptieren(firstnameField.getValue(), lastnameField.getValue());
            entwurfRepository.update(updatedEntwurf);
            this.getUI().ifPresent(it -> it.navigate(SentView.class));
            this.close();
        });
    
        this.add(firstnameField, lastnameField, button);
    }
    
    private boolean isValid() {
        return !firstnameField.isInvalid() && !lastnameField.isInvalid();
    }
}