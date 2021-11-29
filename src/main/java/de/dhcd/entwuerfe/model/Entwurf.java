package de.dhcd.entwuerfe.model;


import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Entwurf {
    
    @NonNull
    private final UUID    uuid;
    @NonNull
    private final byte[]  content;
    
    @NonNull
    private final Integer kundennummer;
    @NonNull
    private final String  kundenname;
    @NonNull
    private final Integer projektnummer;
    @NonNull
    private final String  projektname;
    
    private final EntwurfStatus  status;
    private final String         confirmedByFirstname;
    private final String         confirmedByLastname;
    private final String         confirmedComment;
    private final OffsetDateTime confirmedAt;
    private final OffsetDateTime createdAt;
    
    private Entwurf(Entwurf entwurf, EntwurfStatus status, String confirmedByFirstname, String confirmedByLastname, String confirmedComment) {
        this(
                entwurf.uuid,
                entwurf.content,
                entwurf.getKundennummer(),
                entwurf.getKundenname(),
                entwurf.getProjektnummer(),
                entwurf.getProjektname(),
                status,
                confirmedByFirstname,
                confirmedByLastname,
                confirmedComment,
                OffsetDateTime.now(),
                entwurf.getCreatedAt());
    }
    
    public static Entwurf erstelle(byte[] content, Integer kundennummer, String kundenname, Integer projektnummer, String projektname) {
        Try.of(() -> (content[0] & 0xFF) == 0xFF && (content[1] & 0xD8) == 0xD8).filter(it -> it).getOrElseThrow(
                KeinJPEGBildException::new);
        return new Entwurf(
                UUID.randomUUID(),
                content,
                kundennummer,
                kundenname,
                projektnummer,
                projektname,
                EntwurfStatus.PENDING,
                null,
                null,
                null,
                null,
                OffsetDateTime.now());
    }
    
    public Entwurf akzeptieren(String confirmedByFirstname, String confirmedByLastname) {
        return new Entwurf(this, EntwurfStatus.CONFIRMED, confirmedByFirstname, confirmedByLastname, null);
    }
    
    public Entwurf ablehnen(String firstname, String lastname, String comment) {
        return new Entwurf(this, EntwurfStatus.DECLINED, firstname, lastname, comment);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entwurf entwurf = (Entwurf) o;
        return uuid.equals(entwurf.uuid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
