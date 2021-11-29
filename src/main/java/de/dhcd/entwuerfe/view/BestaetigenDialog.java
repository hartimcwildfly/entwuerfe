package de.dhcd.entwuerfe.view;


import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepository;


public class BestaetigenDialog extends Dialog {
    
    private final TextField firstnameField = new TextField("Vorname");
    private final TextField lastnameField  = new TextField("Nachname");
    private final Button    button         = new Button("Ablehnen");
    
    public BestaetigenDialog(EntwurfRepository entwurfRepository, Entwurf entwurf) {
        firstnameField.setRequired(true);
        lastnameField.setRequired(true);
        
        HasValue.ValueChangeListener valueChangeListener = valueChangeEvent -> button.setEnabled(isValid());
    
        firstnameField.addValueChangeListener(valueChangeListener);
        lastnameField.addValueChangeListener(valueChangeListener);
    
        button.addClickListener(clickEvent -> {
            Entwurf updatedEntwurf = entwurf.akzeptieren(firstnameField.getValue(), lastnameField.getValue());
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