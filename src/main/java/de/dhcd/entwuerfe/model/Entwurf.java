package de.dhcd.entwuerfe.model;


import java.time.OffsetDateTime;
import java.util.UUID;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Entwurf {
    
    private final Integer    id;
    @NonNull private final UUID    uuid;
    @NonNull private final byte[]  content;
    private final Boolean confirmed;
    private final String  confirmedByFirstname;
    private final String  confirmedByLastname;
    private final String confirmedComment;
    private final OffsetDateTime confirmedAt;
    private final OffsetDateTime createdAt;
    
    private Entwurf(Entwurf entwurf, boolean confirmed, String confirmedByFirstname, String confirmedByLastname, String confirmedComment) {
        this(entwurf.id, entwurf.uuid, entwurf.content, confirmed, confirmedByFirstname, confirmedByLastname, confirmedComment, OffsetDateTime.now(), entwurf.getCreatedAt());
    }
    
    
    public static Entwurf erstelle(byte[] content) {
        Try.of(() -> content[0] == 0xFF && content[1] == 0xD8).filter(it -> it).getOrElseThrow(
                KeinJPEGBildException::new);
        return new Entwurf(null, UUID.randomUUID(), content, null, null, null, null, null, OffsetDateTime.now());
    }
    
    public Entwurf akzeptieren(String confirmedByFirstname, String confirmedByLastname) {
        return new Entwurf(this, true, confirmedByFirstname, confirmedByLastname, null);
    }
    
    public Entwurf ablehnen() {
        return new Entwurf(this, true, confirmedByFirstname, confirmedByLastname, confirmedComment);
    }
}
