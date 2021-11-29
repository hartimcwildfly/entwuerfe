CREATE SCHEMA IF NOT EXISTS dhcd;

CREATE TABLE dhcd.entwurf
(
    uuid                    UUID PRIMARY KEY,
    entwurf                 BYTEA                    NOT NULL,
    erstellt_am             TIMESTAMP WITH TIME ZONE NOT NULL,

    kundennummer            INTEGER                  NOT NULL,
    kundenname              VARCHAR                  NOT NULL,
    projektnummer           INTEGER                  NOT NULL,
    projektname             VARCHAR                  NOT NULL,

    akzeptiert              BOOLEAN,
    akzeptiert_von_vorname  VARCHAR,
    akzeptiert_von_nachname VARCHAR,
    akzeptiert_kommentar    VARCHAR,
    akzeptiert_am           TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX ON dhcd.entwurf (uuid);

