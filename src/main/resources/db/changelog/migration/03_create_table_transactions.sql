--liquibase formatted sql
--changeset edvan.ronchi:1 context:test,prod
CREATE TABLE PICPAY.TRANSACTIONS(
    ID NUMERIC(19,0) PRIMARY KEY,
    VALUE NUMERIC(19,0) NOT NULL,
    TIMESTAMP TIMESTAMP(6) NOT NULL,
    PAYER_ID NUMERIC(19,0) NOT NULL,
    PAYEE_ID NUMERIC(19,0) NOT NULL,
    CONSTRAINT FK_TRANSACTIONS_USERS_PAYER_ID FOREIGN KEY (PAYER_ID) REFERENCES PICPAY.USERS(ID),
    CONSTRAINT FK_TRANSACTIONS_USERS_PAYEE_ID FOREIGN KEY (PAYEE_ID) REFERENCES PICPAY.USERS(ID)
);
--rollback DROP TABLE PICPAY.TRANSACTIONS;

--changeset edvan.ronchi:2 context:test,prod
COMMENT ON COLUMN PICPAY.TRANSACTIONS.ID IS 'Identifier';
COMMENT ON COLUMN PICPAY.TRANSACTIONS.VALUE IS 'Value';
COMMENT ON COLUMN PICPAY.TRANSACTIONS.TIMESTAMP IS 'Creation date and time';
COMMENT ON COLUMN PICPAY.TRANSACTIONS.PAYER_ID IS 'Payer';
COMMENT ON COLUMN PICPAY.TRANSACTIONS.PAYEE_ID IS 'Payee';
--rollback not required

--changeset edvan.ronchi:3 context:prod
CREATE SEQUENCE PICPAY.SEQ_TRANSACTIONS MINVALUE 1 MAXVALUE 9999999999 INCREMENT BY 1 START WITH 1;
--rollback DROP SEQUENCE PICPAY.SEQ_TRANSACTIONS;
