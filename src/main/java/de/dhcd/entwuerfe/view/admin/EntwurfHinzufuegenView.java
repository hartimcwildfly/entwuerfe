package de.dhcd.entwuerfe.view.admin;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepository;
import de.dhcd.entwuerfe.model.KeinJPEGBildException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;


@PageTitle("Entwurf hinzufügen")
@Route(value = "entwurf-hinzufuegen", layout = AdminLayout.class)
@Slf4j
public class EntwurfHinzufuegenView extends VerticalLayout {
    
    private final EntwurfRepository entwurfRepository;
    
    public EntwurfHinzufuegenView(EntwurfRepository entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
    
        TextField kundennummer = new TextField("Kundennummer");
        kundennummer.setRequired(true);
    
        TextField kundenname = new TextField("Kundenname");
        kundenname.setRequired(true);
    
        TextField projektname = new TextField("Projektname");
        projektname.setRequired(true);
    
        Upload upload = new Upload();
        upload.setAcceptedFileTypes(".jpeg", ".jpg");
        upload.setMaxFiles(1);
        MemoryBuffer receiver = new MemoryBuffer();
        upload.setReceiver(receiver);
    
        Button createButton = new Button("Abschicken");
        createButton.addClickListener(clickEvent -> {
            Try.of(() -> receiver.getInputStream().readAllBytes()).map(Entwurf::erstelle)
               .onFailure(KeinJPEGBildException.class, ex -> {
                   Notification.show("Kein gültiges JPEG-Bild", 6000, Notification.Position.MIDDLE);
               })
               .onFailure(ex -> {
                   log.error("Fehler beim Upload", ex);
                   Notification.show("Fehler beim Verarbeiten des Uploads", 6000, Notification.Position.MIDDLE);
               })
               .forEach(it -> {
                   entwurfRepository.create(it);
                   this.getUI().ifPresent(ui -> ui.navigate(OffeneEntwuerfeView.class));
               });
        });
        
        this.add(kundenname, kundennummer, projektname, upload, createButton);
    }
}
