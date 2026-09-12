-- liquibase formatted sql

-- changeset feraun:12-change-architecture-for-keycloak

ALTER TABLE users
ADD COLUMN keycloak_id UUID;
ALTER TABLE users
DROP COLUMN password_id;

DROP TABLE passwords;