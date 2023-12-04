--liquibase formatted sql
--changeset edvan.ronchi:1 context:test,prod
CREATE SCHEMA PICPAY;
--rollback DROP SCHEMA PICPAY
