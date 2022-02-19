package de.dhcd.entwuerfe.adapter.api;


import java.util.UUID;
import java.util.stream.Stream;

import de.dhcd.entwuerfe.model.draft.Draft;
import io.vavr.control.Option;


public interface EntwurfRepository {
    void create(Draft entwurf);
    
    void update(Draft entwurf);
    
    Option<Draft> get(UUID uuid);
    
    Stream<Draft> holeOffene();
    
    Stream<Draft> holeArchivierte();
}
