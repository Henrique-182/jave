CREATE TABLE `version` (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME CHAR(6) NOT NULL UNIQUE,
    EFFECTIVE_PERIOD_UNTIL DATE NOT NULL
);