package de.dhcd.entwuerfe.view;


import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepository;


public class AblehnenDialog extends Dialog {
    
    private final TextField commentField   = new TextField("Kommentar");
    private final TextField firstnameField = new TextField("Vorname");
    private final TextField lastnameField  = new TextField("Nachname");
    private final Button    button         = new Button("Bestätigen");
    
    public AblehnenDialog(EntwurfRepository entwurfRepository, Entwurf entwurf) {
        commentField.setRequired(true);
        firstnameField.setRequired(true);
        lastnameField.setRequired(true);
        
        HasValue.ValueChangeListener valueChangeListener = valueChangeEvent -> button.setEnabled(isValid());
        
        commentField.addValueChangeListener(valueChangeListener);
        firstnameField.addValueChangeListener(valueChangeListener);
        lastnameField.addValueChangeListener(valueChangeListener);
        
        button.addClickListener(clickEvent -> {
            Entwurf updatedEntwurf = entwurf.ablehnen(firstnameField.getValue(), lastnameField.getValue(), commentField.getValue());
            entwurfRepository.update(updatedEntwurf);
            this.getUI().ifPresent(it -> it.navigate(SentView.class));
        });
    }
    
    private boolean isValid() {
        return !firstnameField.isInvalid() && !lastnameField.isInvalid() && !commentField.isInvalid();
    }
}
