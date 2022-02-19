package de.dhcd.entwuerfe.view;


import java.io.ByteArrayInputStream;
import java.util.UUID;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import de.dhcd.entwuerfe.adapter.api.EntwurfRepository;
import de.dhcd.entwuerfe.model.draft.Draft;
import io.vavr.control.Option;
import io.vavr.control.Try;


@PageTitle("Entwurf")
@Route(value = "entwurf")
public class EntwurfView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {
    
    private EntwurfRepository entwurfRepository;
    private Option<Draft>     entwurf     = Option.none();
    private Try<UUID>         entwurfUUID = Try.failure(new NullPointerException("Not inital value set"));
    
    private final Button confirmButton = new Button("Annehmen");
    private final Button declineButton = new Button("Ablehnen");
    
    public EntwurfView(EntwurfRepository entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
    
        confirmButton.addClickListener(clickEvent -> {
            new BestaetigenDialog(entwurfRepository, entwurf.get()).open();
        });
        declineButton.addClickListener(clickEvent -> {
            new AblehnenDialog(entwurfRepository, entwurf.get()).open();
        });
    
        this.add(new HorizontalLayout(declineButton, confirmButton));
    
        UI.getCurrent().getSession().getBrowser().getBrowserApplication()
    }
    
    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        entwurfUUID = Try.of(() -> UUID.fromString(s));
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        entwurfUUID.toOption()
                   .flatMap(uuid -> entwurfRepository.get(uuid))
                   .onEmpty(() -> this.getUI().ifPresent(ui -> ui.navigate(ErrorPage.class)))
                   .forEach(this::ladeEntwurf);
    }
    
    private void ladeEntwurf(final Draft entwurf) {
        this.entwurf = Option.of(entwurf);
        Image image = new Image(new StreamResource("ImageName", () -> new ByteArrayInputStream(entwurf.getContent())), "Entwurf");
        image.setMaxHeight(900, Unit.PIXELS);
        image.setMaxWidth(1800, Unit.PIXELS);
        this.add(image);
    }
}
