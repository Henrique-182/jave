CREATE TABLE IBPT_UPDATE (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    FK_COMPANY INT NOT NULL,
    FK_VERSION INT NOT NULL,
    IS_UPDATED TINYINT DEFAULT 0,
    UNIQUE KEY `UNIQUE_IBPTUPDATE` (`FK_COMPANY`, `FK_VERSION`)
);
 
ALTER TABLE IBPT_UPDATE ADD CONSTRAINT FK_COMPANY_IBPTUPDATE
    FOREIGN KEY (FK_COMPANY)
    REFERENCES COMPANY (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE IBPT_UPDATE ADD CONSTRAINT FK_VERSION_IBPTUPDATE
    FOREIGN KEY (FK_VERSION)
    REFERENCES VERSION (ID)
    ON DELETE RESTRICT;