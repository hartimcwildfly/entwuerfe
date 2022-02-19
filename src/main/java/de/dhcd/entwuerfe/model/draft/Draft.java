package de.dhcd.entwuerfe.model.draft;


import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import de.dhcd.entwuerfe.model.EntwurfStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Draft {
    
    @NonNull
    private final UUID   uuid;
    @NonNull
    private final byte[] content;
    
    @NonNull
    private final Integer        kundennummer;
    @NonNull
    private final String         kundenname;
    @NonNull
    private final Integer projektnummer;
    @NonNull
    private final String  projektname;
    @NonNull
    private final OffsetDateTime createdAt;
    
    public abstract EntwurfStatus getStatus();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Draft entwurf = (Draft) o;
        return uuid.equals(entwurf.uuid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
