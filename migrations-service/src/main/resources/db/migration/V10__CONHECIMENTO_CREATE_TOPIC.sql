CREATE TABLE IF NOT EXISTS CONHECIMENTO.TB_TOPIC (
    ID SERIAL CONSTRAINT PK_TB_TOPIC PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL CONSTRAINT UC_TB_TOPIC_NAME UNIQUE
);