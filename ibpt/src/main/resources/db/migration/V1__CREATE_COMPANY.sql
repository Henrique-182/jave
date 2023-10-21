﻿CREATE TABLE IF NOT EXISTS `company` (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CNPJ CHAR(14) NOT NULL UNIQUE,
    TRADE_NAME VARCHAR(255) NOT NULL,
    BUSINESS_NAME VARCHAR(255) NOT NULL,
    SOFTWARE ENUM('STAC', 'ESTI') NOT NULL,
    CONNECTION VARCHAR(100) NOT NULL,
    HAVE_AUTHORIZATION TINYINT DEFAULT TRUE,
    OBSERVATION VARCHAR(255) DEFAULT NULL,
    IS_ACTIVE TINYINT DEFAULT TRUE,
    FK_COMPANY_SAME_DB INT DEFAULT NULL
);

ALTER TABLE `company` ADD CONSTRAINT FK_COMPANY_COMPANY_SAME_DB
    FOREIGN KEY (FK_COMPANY_SAME_DB)
    REFERENCES `company` (ID);