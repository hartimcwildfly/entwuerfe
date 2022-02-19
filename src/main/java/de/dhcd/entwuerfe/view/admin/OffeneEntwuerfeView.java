package de.dhcd.entwuerfe.view.admin;


import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import de.dhcd.entwuerfe.adapter.api.EntwurfRepository;
import de.dhcd.entwuerfe.model.draft.Draft;
import de.dhcd.entwuerfe.view.EntwurfView;


@PageTitle("Offene Entwürfe")
@Route(value = "entwuerfe", layout = AdminLayout.class)
@RouteAlias(value = "", layout = AdminLayout.class)
public class OffeneEntwuerfeView extends HorizontalLayout implements AfterNavigationObserver {
    
    private final EntwurfRepository entwurfRepository;
    private       Grid<Draft>       entwurfGrid;
    
    public OffeneEntwuerfeView(EntwurfRepository entwurfRepository) {
        this.entwurfRepository = entwurfRepository;
        
        entwurfGrid = new Grid<>();
        entwurfGrid.addColumn(it -> it.getUuid().toString()).setHeader("UUID");
        entwurfGrid.addColumn(it -> it.getKundennummer()).setHeader("Kundennummer").setSortable(true);
        entwurfGrid.addColumn(it -> it.getKundenname()).setHeader("Kundenname");
        entwurfGrid.addColumn(it -> it.getProjektnummer()).setHeader("Projektnummer").setSortable(true);
        entwurfGrid.addColumn(it -> it.getProjektname()).setHeader("Projektname");
        entwurfGrid.addColumn(it -> it.getCreatedAt()).setHeader("Erstellt am").setSortable(true);
        entwurfGrid.addColumn(it -> it.getStatus().getLabel()).setHeader("Status");
        entwurfGrid.addComponentColumn(this::test);
    
        this.add(entwurfGrid);
    }
    
    private Component test(Draft entwurf) {
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
    
    private void ladeAlle(Stream<Draft> entwurfStream) {
        ListDataProvider<Draft> entwurfListDataProvider = DataProvider.fromStream(entwurfStream);
        entwurfGrid.setDataProvider(entwurfListDataProvider);
    }
    
}
