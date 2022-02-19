package de.dhcd.entwuerfe.adapter.inmemory;


import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import de.dhcd.entwuerfe.adapter.api.EntwurfRepository;
import de.dhcd.entwuerfe.model.EntwurfStatus;
import de.dhcd.entwuerfe.model.draft.Draft;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;


@Repository
@ApplicationScope
@Profile("!persistence")
public class EnwturfRepositoryInMemory implements EntwurfRepository {
    
    private Set<Draft> entwuerfe = HashSet.empty();
    
    @Override
    public void create(Draft entwurf) {
        entwuerfe = entwuerfe.add(entwurf);
    }
    
    @Override
    public void update(Draft entwurf) {
        entwuerfe = entwuerfe.remove(entwurf).add(entwurf);
    }
    
    @Override
    public Option<Draft> get(UUID uuid) {
        return entwuerfe.filter(it -> it.getUuid().equals(uuid)).singleOption();
    }
    
    @Override
    public Stream<Draft> holeOffene() {
        return entwuerfe.filter(it -> it.getStatus() == EntwurfStatus.PENDING)
                        .toJavaStream().sorted(Comparator.comparing(Draft::getCreatedAt).reversed());
    }
    
    @Override
    public Stream<Draft> holeArchivierte() {
        return entwuerfe.filter(it -> it.getStatus() == EntwurfStatus.APPROVED || it.getStatus() == EntwurfStatus.DECLINED)
                        .toJavaStream().sorted(Comparator.comparing(Draft::getCreatedAt).reversed());
    }
}
