CREATE TABLE IF NOT EXISTS EMAIL.TB_EMAIL (
    ID SERIAL CONSTRAINT FK_TB_EMAIL PRIMARY KEY,
	EMAIL_FROM VARCHAR(255) NOT NULL,
    EMAIL_TO VARCHAR(255) NOT NULL,
	SUBJECT VARCHAR(255) NOT NULL,
    CONTENT TEXT NOT NULL,
    SERVICE_NAME SMALLINT NOT NULL CONSTRAINT TB_EMAIL_STATUS_CHECK CHECK (SERVICE_NAME >= 0 AND SERVICE_NAME <= 5),
    STATUS SMALLINT NOT NULL CONSTRAINT CHK_TB_EMAIL_STATUS CHECK (STATUS >= 0 AND STATUS <= 1),
    CREATION_DATETIME TIMESTAMP(6) NOT NULL,
    LAST_UPDATE_DATETIME TIMESTAMP(6) NOT NULL CONSTRAINT CHK_TB_EMAIL_LAST_UPDATE_DATETIME CHECK (LAST_UPDATE_DATETIME >= CREATION_DATETIME)
);
