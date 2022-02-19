package de.dhcd.entwuerfe.adapter.impl;


import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.generated.dhcd.Tables;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import de.dhcd.entwuerfe.adapter.api.EntwurfRepository;
import de.dhcd.entwuerfe.model.EntwurfMapper;
import de.dhcd.entwuerfe.model.draft.Draft;
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
    public void create(Draft entwurf) {
        dslContext.executeInsert(EntwurfMapper.toRecord(entwurf));
    }
    
    @Override
    public void update(Draft entwurf) {
        dslContext.executeUpdate(EntwurfMapper.toRecord(entwurf));
    }
    
    @Override
    public Option<Draft> get(UUID uuid) {
        return Try.of(() -> dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.UUID.eq(uuid)).fetchSingle())
                  .map(EntwurfMapper::toModel)
                  .map(Option::of)
                  .recover(NoDataFoundException.class, Option.none())
                  .get();
    }
    
    @Override
    public Stream<Draft> holeOffene() {
        return dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.AKZEPTIERT.isNull()).fetch().stream().map(EntwurfMapper::toModel)
                         .sorted(Comparator.comparing(Draft::getCreatedAt).reversed());
    }
    
    @Override
    public Stream<Draft> holeArchivierte() {
        return dslContext.selectFrom(Tables.ENTWURF).where(Tables.ENTWURF.AKZEPTIERT.isNotNull()).fetch().stream().map(EntwurfMapper::toModel)
                         .sorted(Comparator.comparing(Draft::getCreatedAt).reversed());
    }
}
