CREATE TABLE IF NOT EXISTS SISTEMAS.TB_SOFTWARE (
    ID SERIAL CONSTRAINT PK_TB_SOFTWARE PRIMARY KEY,
    NAME varchar(100) NOT NULL CONSTRAINT UC_TB_SOFTWARE_NAME UNIQUE
);
