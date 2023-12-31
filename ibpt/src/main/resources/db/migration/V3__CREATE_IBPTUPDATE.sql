CREATE TABLE `ibpt_update` (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    FK_COMPANY INT NOT NULL,
    FK_VERSION INT NOT NULL,
    IS_UPDATED TINYINT DEFAULT FALSE,
    UNIQUE KEY `UNIQUE_IBPTUPDATE` (`FK_COMPANY`, `FK_VERSION`)
);
 
ALTER TABLE `ibpt_update` ADD CONSTRAINT FK_COMPANY_IBPTUPDATE
    FOREIGN KEY (FK_COMPANY)
    REFERENCES `company` (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE `ibpt_update` ADD CONSTRAINT FK_VERSION_IBPTUPDATE
    FOREIGN KEY (FK_VERSION)
    REFERENCES `version` (ID)
    ON DELETE RESTRICT;