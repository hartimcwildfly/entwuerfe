package de.dhcd.entwuerfe.model;


import java.util.UUID;
import java.util.stream.Stream;

import io.vavr.control.Option;


public interface EntwurfRepository {
    void create(Entwurf entwurf);
    
    void update(Entwurf entwurf);
    
    Option<Entwurf> get(UUID uuid);
    
    Stream<Entwurf> holeOffene();
    
    Stream<Entwurf> holeArchivierte();
}
