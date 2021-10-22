package de.dhcd.entwuerfe.view;


import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.jooq.generated.tables.records.EntwurfRecord;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepository;


@PageTitle("Entwurf")
@Route(value = "entwurf")
public class EntwurfView extends HorizontalLayout implements HasUrlParameter<String>, AfterNavigationObserver {
    
    private TextField name;
    private Button    sayHello;
    private Image entwurf;
    
    private EntwurfRepository entwurfRepository;
    private UUID entwurfUUID;
    
    public EntwurfView(EntwurfRepository entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
        addClassName("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }
    
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        entwurfUUID = UUID.fromString(s);
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        entwurfRepository.get(entwurfUUID).ifPresentOrElse(this::ladeEntwurf, () -> System.out.println("TODO navigate to Error page"));
    }
    
    private void ladeEntwurf(final Entwurf entwurf) {
        this.add(new Image(new StreamResource("ImageName", () -> new ByteArrayInputStream(entwurf.getContent())), "Entwurf"));
    }
}
