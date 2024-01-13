CREATE TABLE IF NOT EXISTS CONHECIMENTO.TB_KNOWLEDGE_TOPIC (
    FK_KNOWLEDGE INT NOT NULL,
    FK_TOPIC INT NOT NULL
);
 
ALTER TABLE CONHECIMENTO.TB_KNOWLEDGE_TOPIC ADD CONSTRAINT FK_TB_KNOWLEDGE
    FOREIGN KEY (FK_KNOWLEDGE)
    REFERENCES CONHECIMENTO.TB_KNOWLEDGE (ID);
 
ALTER TABLE CONHECIMENTO.TB_KNOWLEDGE_TOPIC ADD CONSTRAINT FK_TB_TOPIC
    FOREIGN KEY (FK_TOPIC)
    REFERENCES CONHECIMENTO.TB_TOPIC (ID);
