package de.dhcd.entwuerfe.view;


import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.dhcd.entwuerfe.model.Entwurf;
import de.dhcd.entwuerfe.model.EntwurfRepository;


@PageTitle("Adminübersicht")
@Route(value = "admin")
public class AdminView extends HorizontalLayout implements AfterNavigationObserver {
    
    private final EntwurfRepository entwurfRepository;
    private Grid<Entwurf> entwurfGrid;
    
    public AdminView(EntwurfRepository entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
    
        entwurfGrid = new Grid<>();
        entwurfGrid.addColumn(it -> it.getUuid().toString()).setHeader("UUID");
        entwurfGrid.addComponentColumn(this::test);
        
        this.add(entwurfGrid);
    }
    
    private Component test(Entwurf entwurf) {
        Button showPermissionsButton = new Button("Springe zu");
        showPermissionsButton.addClickListener(buttonClickEvent ->
                                                       this.getUI().ifPresent(it -> it.navigate(EntwurfView.class, entwurf.getUuid().toString()))
        );
        return showPermissionsButton;
    }
    
    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        ladeAlle(entwurfRepository.holeOffene());
    }
    
    private void ladeAlle(Stream<Entwurf> entwurfStream) {
        ListDataProvider<Entwurf> entwurfListDataProvider = DataProvider.fromStream(entwurfStream);
        entwurfGrid.setDataProvider(entwurfListDataProvider);
    }
    
}
