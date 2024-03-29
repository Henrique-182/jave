CREATE TABLE IF NOT EXISTS CONHECIMENTO.TB_KNOWLEDGE (
    ID SERIAL CONSTRAINT PK_TB_KNOWLEDGE PRIMARY KEY,
    TITLE VARCHAR(100) NOT NULL,
    DESCRIPTION VARCHAR(100) DEFAULT NULL,
    FK_SOFTWARE INT NOT NULL,
    CONTENT TEXT NOT NULL
);
 
ALTER TABLE CONHECIMENTO.TB_KNOWLEDGE ADD CONSTRAINT FK_SOFTWARE
    FOREIGN KEY (FK_SOFTWARE)
    REFERENCES SISTEMAS.TB_SOFTWARE (ID);
