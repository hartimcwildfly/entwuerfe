package de.dhcd.entwuerfe.model;


import lombok.Getter;


public enum EntwurfStatus {
    CONFIRMED("Bestätigt"), DECLINED("Abgelehnt"), PENDING("Offen");
    
    @Getter
    private final String label;
    
    EntwurfStatus(String label) {
        this.label = label;
    }
}
