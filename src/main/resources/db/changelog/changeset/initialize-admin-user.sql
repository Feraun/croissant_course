-- liquibase formatted sql

-- changeset feraun:11-create-admin-user
INSERT INTO passwords (id, password)
VALUES
    (1, '$2a$10$jP.GPz60Px5vjNcELPvQQ.LbbWhd.22anzHkCQW1kVOP7t1wwOMGO');

INSERT INTO users (id, username, enabled, role, first_name, last_name, contact_number, email, created_at, password_id)
VALUES
    (1, 'admin', true, 'ADMIN', 'grib', 'artem', '52526', 'sads@dsfs.com', NOW(), 1);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

SELECT setval('passwords_id_seq', (SELECT MAX(id) FROM passwords));









