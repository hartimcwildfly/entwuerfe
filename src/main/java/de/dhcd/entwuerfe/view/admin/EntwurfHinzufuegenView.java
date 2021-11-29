package de.dhcd.entwuerfe.view.admin;


import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepositoryJooq;
import de.dhcd.entwuerfe.model.KeinJPEGBildException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;


@PageTitle("Entwurf hinzufügen")
@Route(value = "entwurf-hinzufuegen", layout = AdminLayout.class)
@Slf4j
public class EntwurfHinzufuegenView extends VerticalLayout {
    
    private final EntwurfRepositoryJooq entwurfRepository;
    
    public EntwurfHinzufuegenView(EntwurfRepositoryJooq entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
        
        IntegerField kundennummer = new IntegerField("Kundennummer");
        kundennummer.setRequiredIndicatorVisible(true);
        kundennummer.setInvalid(true);
        kundennummer.setMin(10_000);
        kundennummer.setMax(99_999);
        kundennummer.setErrorMessage("Kundennummer muss zwischen 10000 und 99999 sein");
        
        TextField kundenname = new TextField("Kundenname");
        kundenname.setRequired(true);
        kundenname.setInvalid(true);
    
        IntegerField projektnummer = new IntegerField("Projektnummer");
        projektnummer.setRequiredIndicatorVisible(true);
        projektnummer.setInvalid(true);
        projektnummer.setMin(10_000);
        projektnummer.setMax(99_999);
        kundennummer.setErrorMessage("Projektnummer muss zwischen 10000 und 99999 sein");
    
        TextField projektname = new TextField("Projektname");
        projektname.setRequired(true);
        projektname.setInvalid(true);
    
        Button createButton = new Button("Abschicken");
        createButton.setEnabled(false);
    
        Upload upload = new Upload();
        upload.setAcceptedFileTypes(".jpeg", ".jpg");
        upload.setMaxFiles(1);
        MemoryBuffer receiver = new MemoryBuffer();
        upload.setReceiver(receiver);
    
        HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<?, ?>> componentValueChangeEventValueChangeListener =
                ignored -> {
                    createButton.setEnabled(!kundennummer.isInvalid()
                                            && !kundenname.isInvalid()
                                            && !projektnummer.isInvalid()
                                            && !projektname.isInvalid()
                                            && Try.of(() -> (MemoryBuffer) upload.getReceiver())
                                                  .mapTry(it -> it.getInputStream().available() > 0)
                                                  .getOrElse(false));
                };
    
        kundennummer.addValueChangeListener(componentValueChangeEventValueChangeListener);
        kundenname.addValueChangeListener(componentValueChangeEventValueChangeListener);
        projektname.addValueChangeListener(componentValueChangeEventValueChangeListener);
        projektnummer.addValueChangeListener(componentValueChangeEventValueChangeListener);
    
        upload.getElement().addEventListener("file-remove", ignored -> {
            upload.setReceiver(new MemoryBuffer());
            createButton.setEnabled(!kundennummer.isInvalid()
                                    && !kundenname.isInvalid()
                                    && !projektnummer.isInvalid()
                                    && !projektname.isInvalid()
                                    && Try.of(() -> (MemoryBuffer) upload.getReceiver())
                                          .mapTry(it -> it.getInputStream().available() > 0)
                                          .getOrElse(false));
        });
        upload.addSucceededListener(ignored -> {
            createButton.setEnabled(!kundennummer.isInvalid()
                                    && !kundenname.isInvalid()
                                    && !projektnummer.isInvalid()
                                    && !projektname.isInvalid()
                                    && Try.of(() -> (MemoryBuffer) upload.getReceiver())
                                          .mapTry(it -> it.getInputStream().available() > 0)
                                          .getOrElse(false));
        });
    
        createButton.addClickListener(clickEvent -> {
            Try.of(() -> (MemoryBuffer) upload.getReceiver()).mapTry(it -> it.getInputStream().readAllBytes())
               .onFailure(KeinJPEGBildException.class, ex -> {
                   Notification.show("Kein gültiges JPEG-Bild", 6000, Notification.Position.MIDDLE);
               })
               .onFailure(ex -> {
                   log.error("Fehler beim Upload", ex);
                   Notification.show("Fehler beim Verarbeiten des Uploads", 6000, Notification.Position.MIDDLE);
               })
               .map(content ->
                            Entwurf.erstelle(
                                    content,
                                    kundennummer.getValue(),
                                    kundenname.getValue(),
                                    projektnummer.getValue(),
                                    projektname.getValue())
               )
               .forEach(it -> {
                   entwurfRepository.create(it);
                   this.getUI().ifPresent(ui -> ui.navigate(OffeneEntwuerfeView.class));
               });
        });
    
        this.add(kundennummer, kundenname, projektnummer, projektname, upload, createButton);
    }
}
