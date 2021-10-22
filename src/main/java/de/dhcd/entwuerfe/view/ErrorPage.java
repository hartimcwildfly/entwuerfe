package de.dhcd.entwuerfe.view;


import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Fehler")
@Route(value = "error")
public class ErrorPage extends HorizontalLayout {
    
    public ErrorPage() {
        this.add(new Label("Kein Entwurf mit dieser ID gefunden!"));
    }
}
