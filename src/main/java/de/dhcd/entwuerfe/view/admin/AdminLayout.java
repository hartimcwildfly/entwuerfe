package de.dhcd.entwuerfe.view.admin;


import static com.vaadin.flow.component.tabs.Tabs.Orientation.VERTICAL;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLink;

@RoutePrefix("admin")
public class AdminLayout extends AppLayout {
    
    private       H1                   viewTitle    = new H1();
    private final HeaderContent        headerContent;
    
    public AdminLayout() {
        headerContent = new HeaderContent();
        
        setPrimarySection(Section.NAVBAR);
        addToNavbar(false, headerContent);
        addToDrawer(createDrawerContent());
    }
    
    private Component createDrawerContent() {
        Tabs tabs = new Tabs(
                new Tab(new RouterLink("Offene Entwürfe", OffeneEntwuerfeView.class)),
                new Tab(new RouterLink("Entwurf hinzufügen", EntwurfHinzufuegenView.class))
        );
        tabs.setOrientation(VERTICAL);
        return tabs;
    }
    
    private String getCurrentPageTitle() {
        return getContent().getClass().getAnnotation(PageTitle.class).value();
    }
    
    
    private class HeaderContent extends HorizontalLayout {
        HeaderContent() {
            setId("header");
            getThemeList().set("dark", true);
            setWidthFull();
            setSpacing(true);
            setAlignItems(FlexComponent.Alignment.CENTER);
            
            add(new DrawerToggle());
            add(viewTitle);
        }
    }
}
