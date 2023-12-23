﻿CREATE TABLE IF NOT EXISTS `user_permission` (
    FK_USER INT,
    FK_PERMISSION INT,
    PRIMARY KEY (FK_USER, FK_PERMISSION)
);

ALTER TABLE `user_permission` ADD CONSTRAINT FK_USERS_USERPERMISSION
    FOREIGN KEY (FK_USER)
    REFERENCES `users` (ID);
 
ALTER TABLE `user_permission` ADD CONSTRAINT FK_PERMISSION_USERPERMISSION
    FOREIGN KEY (FK_PERMISSION)
    REFERENCES `permission` (ID);