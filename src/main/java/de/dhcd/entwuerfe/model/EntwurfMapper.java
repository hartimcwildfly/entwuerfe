package de.dhcd.entwuerfe.model;


import org.jooq.generated.tables.records.EntwurfRecord;

import io.vavr.control.Option;


public class EntwurfMapper {
    
    public static EntwurfRecord toRecord(Entwurf entwurf) {
        EntwurfRecord entwurfRecord = new EntwurfRecord();
    
        Option.of(entwurf.getId()).forEach(entwurfRecord::setId); // setId(null) and not calling setId at all results in different behaviour
        entwurfRecord.setUuid(entwurf.getUuid());
        entwurfRecord.setEntwurf(entwurf.getContent());
    
        entwurfRecord.setKundennummer(entwurf.getKundennummer());
        entwurfRecord.setKundenname(entwurf.getKundenname());
        entwurfRecord.setProjektnummer(entwurf.getProjektnummer());
        entwurfRecord.setProjektname(entwurf.getProjektname());
    
        entwurfRecord.setAkzeptiert(entwurf.getConfirmed());
        entwurfRecord.setAkzeptiertVonVorname(entwurf.getConfirmedByFirstname());
        entwurfRecord.setAkzeptiertVonNachname(entwurf.getConfirmedByLastname());
        entwurfRecord.setAkzeptiertKommentar(entwurf.getConfirmedComment());
        entwurfRecord.setAkzeptiertAm(entwurf.getConfirmedAt());
        entwurfRecord.setErstelltAm(entwurf.getCreatedAt());
        return entwurfRecord;
    }
    
    public static Entwurf toModel(EntwurfRecord entwurfRecord ) {
        return new Entwurf(entwurfRecord.getId(), entwurfRecord.getUuid(), entwurfRecord.getEntwurf(),
                           entwurfRecord.getKundennummer(),
                           entwurfRecord.getKundenname(),
                           entwurfRecord.getProjektnummer(),
                           entwurfRecord.getProjektname(),
                           entwurfRecord.getAkzeptiert(),
                           entwurfRecord.getAkzeptiertVonVorname(),
                           entwurfRecord.getAkzeptiertVonNachname(),
                           entwurfRecord.getAkzeptiertKommentar(),
                           entwurfRecord.getAkzeptiertAm(),
                           entwurfRecord.getErstelltAm()
        );
    }
}
