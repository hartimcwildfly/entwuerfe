package de.dhcd.entwuerfe.model.draft;


import java.time.OffsetDateTime;
import java.util.UUID;

import de.dhcd.entwuerfe.model.Approver;
import de.dhcd.entwuerfe.model.EntwurfStatus;
import de.dhcd.entwuerfe.model.KeinJPEGBildException;
import io.vavr.control.Try;
import lombok.NonNull;


public class PendingDraft extends Draft {
    
    private PendingDraft(
            @NonNull UUID uuid,
            @NonNull byte[] content,
            @NonNull Integer kundennummer,
            @NonNull String kundenname,
            @NonNull Integer projektnummer, @NonNull String projektname, OffsetDateTime createdAt) {
        super(uuid, content, kundennummer, kundenname, projektnummer, projektname, createdAt);
    }
    
    @Override
    public EntwurfStatus getStatus() {
        return EntwurfStatus.PENDING;
    }
    
    public static Draft erstelle(byte[] content, Integer kundennummer, String kundenname, Integer projektnummer, String projektname) {
        Try.of(() -> (content[0] & 0xFF) == 0xFF && (content[1] & 0xD8) == 0xD8).filter(it -> it).getOrElseThrow(
                KeinJPEGBildException::new);
        return new PendingDraft(
                UUID.randomUUID(),
                content,
                kundennummer,
                kundenname,
                projektnummer,
                projektname,
                OffsetDateTime.now());
    }
    
    public Draft akzeptieren(Approver approver) {
        return new ApprovedDraft(this, approver, null, OffsetDateTime.now());
    }
    
    public Draft ablehnen(Approver approver, String comment) {
        return new DeclinedDraft(this, approver, comment, OffsetDateTime.now());
    }
}
