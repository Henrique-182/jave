CREATE TYPE SISTEMAS.TP_SOFTWARE_TYPE AS ENUM ('Geral', 'Fiscal');
CREATE CAST (VARCHAR AS SISTEMAS.TP_SOFTWARE_TYPE) WITH INOUT AS IMPLICIT;

CREATE TABLE SISTEMAS.TB_COMPANY_SOFTWARE (
    ID SERIAL CONSTRAINT PK_TB_COMPANY_SOFTWARE PRIMARY KEY,
    FK_COMPANY INT NOT NULL,
    FK_SOFTWARE INT NOT NULL,
    TYPE SISTEMAS.TP_SOFTWARE_TYPE NOT NULL,
    HAVE_AUTHORIZATION BOOLEAN NOT NULL,
    CONNECTION_ID VARCHAR(100) NOT NULL,
    OBSERVATION VARCHAR(255) NOT NULL,
    IS_ACTIVE BOOLEAN DEFAULT TRUE NOT NULL,
    FK_COMPANY_SOFTWARE_SAME_DB INT DEFAULT NULL
);

ALTER TABLE SISTEMAS.TB_COMPANY_SOFTWARE ADD CONSTRAINT FK_SOFTWARE
    FOREIGN KEY (FK_SOFTWARE)
    REFERENCES SISTEMAS.TB_SOFTWARE (ID);
 
ALTER TABLE SISTEMAS.TB_COMPANY_SOFTWARE ADD CONSTRAINT FK_COMPANY
    FOREIGN KEY (FK_COMPANY)
    REFERENCES SISTEMAS.TB_COMPANY (ID);
 
ALTER TABLE SISTEMAS.TB_COMPANY_SOFTWARE ADD CONSTRAINT FK_COMPANYSOFTWARE_SAME_DB
    FOREIGN KEY (FK_COMPANY_SOFTWARE_SAME_DB)
    REFERENCES SISTEMAS.TB_COMPANY_SOFTWARE (ID);
