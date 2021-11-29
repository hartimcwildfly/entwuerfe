package de.dhcd.entwuerfe.model;


import org.jooq.generated.dhcd.tables.records.EntwurfRecord;


public class EntwurfMapper {
    
    public static EntwurfRecord toRecord(Entwurf entwurf) {
        EntwurfRecord entwurfRecord = new EntwurfRecord();
    
        entwurfRecord.setUuid(entwurf.getUuid());
        entwurfRecord.setEntwurf(entwurf.getContent());
    
        entwurfRecord.setKundennummer(entwurf.getKundennummer());
        entwurfRecord.setKundenname(entwurf.getKundenname());
        entwurfRecord.setProjektnummer(entwurf.getProjektnummer());
        entwurfRecord.setProjektname(entwurf.getProjektname());
    
        entwurfRecord.setAkzeptiert(mapStatus(entwurf.getStatus()));
        entwurfRecord.setAkzeptiertVonVorname(entwurf.getConfirmedByFirstname());
        entwurfRecord.setAkzeptiertVonNachname(entwurf.getConfirmedByLastname());
        entwurfRecord.setAkzeptiertKommentar(entwurf.getConfirmedComment());
        entwurfRecord.setAkzeptiertAm(entwurf.getConfirmedAt());
        entwurfRecord.setErstelltAm(entwurf.getCreatedAt());
        return entwurfRecord;
    }
    
    public static Entwurf toModel(EntwurfRecord entwurfRecord) {
        return new Entwurf(entwurfRecord.getUuid(), entwurfRecord.getEntwurf(),
                           entwurfRecord.getKundennummer(),
                           entwurfRecord.getKundenname(),
                           entwurfRecord.getProjektnummer(),
                           entwurfRecord.getProjektname(),
                           mapStatusToModel(entwurfRecord.getAkzeptiert()),
                           entwurfRecord.getAkzeptiertVonVorname(),
                           entwurfRecord.getAkzeptiertVonNachname(),
                           entwurfRecord.getAkzeptiertKommentar(),
                           entwurfRecord.getAkzeptiertAm(),
                           entwurfRecord.getErstelltAm()
        );
    }
    
    private static EntwurfStatus mapStatusToModel(Boolean confirmed) {
        return switch (confirmed) {
            case null -> EntwurfStatus.PENDING;
            case Boolean b -> b ? EntwurfStatus.CONFIRMED : EntwurfStatus.DECLINED;
        };
    }
    
    private static Boolean mapStatus(EntwurfStatus entwurfStatus) {
        return switch (entwurfStatus) {
            case CONFIRMED -> true;
            case DECLINED -> false;
            case PENDING -> null;
        };
    }
}
