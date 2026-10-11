-- liquibase formatted sql

-- changeset feraun:13-one-institution-to-many-managers

UPDATE users
SET institution_id = institutions.id
FROM institutions
WHERE users.id = institutions.user_id;

-- 2. Удаляем старый внешний ключ
ALTER TABLE institutions
DROP CONSTRAINT fk_institution_user;

-- 3. Удаляем неактуальную колонку
ALTER TABLE institutions
DROP COLUMN user_id;