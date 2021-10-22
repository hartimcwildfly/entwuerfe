package de.dhcd.entwuerfe.model;


import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.generated.Tables;
import org.jooq.generated.tables.records.EntwurfRecord;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import io.vavr.control.Option;
import io.vavr.control.Try;


@Repository
@ApplicationScope
public class EntwurfRepository {
    
    private final DSLContext dslContext;
    
    public EntwurfRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    public Stream<Entwurf> holeOffene() {
        return dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.AKZEPTIERT.isNull()).fetch().stream().map(EntwurfMapper::toModel);
    }
    
    public void create(Entwurf entwurf) {
        EntwurfRecord entwurfRecord = EntwurfMapper.toRecord(entwurf);
        entwurfRecord.attach(dslContext.configuration());
        entwurfRecord.store();
    }
    
    public void update(Entwurf entwurf) {
        dslContext.executeUpdate(EntwurfMapper.toRecord(entwurf));
    }
    
    public Optional<Entwurf> get(UUID uuid) {
        return Try.of(() -> dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.UUID.eq(uuid)).fetchSingle())
                  .map(EntwurfMapper::toModel)
                  .map(Optional::of)
                  .recover(NoDataFoundException.class, Optional.empty())
                  .get();
    }
}
