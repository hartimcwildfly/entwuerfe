package de.dhcd.entwuerfe.model;


import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;


@Repository
@ApplicationScope
@Profile("!persistence")
public class EnwturfRepositoryInMemory implements EntwurfRepository {
    
    private Set<Entwurf> entwuerfe = HashSet.empty();
    
    @Override
    public void create(Entwurf entwurf) {
        entwuerfe = entwuerfe.add(entwurf);
    }
    
    @Override
    public void update(Entwurf entwurf) {
        entwuerfe = entwuerfe.remove(entwurf).add(entwurf);
    }
    
    @Override
    public Option<Entwurf> get(UUID uuid) {
        return entwuerfe.filter(it -> it.getUuid().equals(uuid)).singleOption();
    }
    
    @Override
    public Stream<Entwurf> holeOffene() {
        return entwuerfe.filter(it -> it.getStatus() == EntwurfStatus.PENDING)
                        .toJavaStream().sorted(Comparator.comparing(Entwurf::getCreatedAt).reversed());
    }
    
    @Override
    public Stream<Entwurf> holeArchivierte() {
        return entwuerfe.filter(it -> it.getStatus() == EntwurfStatus.CONFIRMED || it.getStatus() == EntwurfStatus.DECLINED)
                        .toJavaStream().sorted(Comparator.comparing(Entwurf::getCreatedAt).reversed());
    }
}
