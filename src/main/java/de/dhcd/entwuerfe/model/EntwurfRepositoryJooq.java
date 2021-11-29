package de.dhcd.entwuerfe.model;


import java.util.UUID;
import java.util.stream.Stream;

import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.generated.dhcd.Tables;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import io.vavr.control.Option;
import io.vavr.control.Try;


@Repository
@ApplicationScope
@Profile("persistence")
public class EntwurfRepositoryJooq implements EntwurfRepository {
    
    private final DSLContext dslContext;
    
    public EntwurfRepositoryJooq(DSLContext dslContext) {
        this.dslContext = dslContext;
    }
    
    @Override
    public void create(Entwurf entwurf) {
        dslContext.executeInsert(EntwurfMapper.toRecord(entwurf));
    }
    
    @Override
    public void update(Entwurf entwurf) {
        dslContext.executeUpdate(EntwurfMapper.toRecord(entwurf));
    }
    
    @Override
    public Option<Entwurf> get(UUID uuid) {
        return Try.of(() -> dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.UUID.eq(uuid)).fetchSingle())
                  .map(EntwurfMapper::toModel)
                  .map(Option::of)
                  .recover(NoDataFoundException.class, Option.none())
                  .get();
    }
    
    @Override
    public Stream<Entwurf> holeOffene() {
        return dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.AKZEPTIERT.isNull()).fetch().stream().map(EntwurfMapper::toModel);
    }
}
