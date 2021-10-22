package de.dhcd.entwuerfe.model;


import java.util.UUID;
import java.util.stream.Stream;

import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.generated.Tables;
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
    
    public void create(Entwurf entwurf) {
        dslContext.executeInsert(EntwurfMapper.toRecord(entwurf));
    }
    
    public void update(Entwurf entwurf) {
        dslContext.executeUpdate(EntwurfMapper.toRecord(entwurf));
    }
    
    public Option<Entwurf> get(UUID uuid) {
        return Try.of(() -> dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.UUID.eq(uuid)).fetchSingle())
                  .map(EntwurfMapper::toModel)
                  .map(Option::of)
                  .recover(NoDataFoundException.class, Option.none())
                  .get();
    }
    
    public Stream<Entwurf> holeOffene() {
        return dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.AKZEPTIERT.isNull()).fetch().stream().map(EntwurfMapper::toModel);
    }
}
