ALTER TABLE `knowledge` ADD COLUMN FK_USER_LAST_UPDATE INT NOT NULL DEFAULT 1;

ALTER TABLE `knowledge` ADD COLUMN LAST_UPDATE_DATETIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP();

ALTER TABLE `knowledge` ADD COLUMN FK_USER_CREATION INT NOT NULL DEFAULT 1;

ALTER TABLE `knowledge` ADD COLUMN CREATION_DATETIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP();

ALTER TABLE `knowledge` ADD CONSTRAINT FK_USER_KNOWLEDGE_LAST_UPDATE
FOREIGN KEY (FK_USER_LAST_UPDATE)
REFERENCES `users` (ID);

ALTER TABLE `knowledge` ADD CONSTRAINT FK_USER_KNOWLEDGE_CREATION
FOREIGN KEY (FK_USER_CREATION)
REFERENCES `users` (ID);