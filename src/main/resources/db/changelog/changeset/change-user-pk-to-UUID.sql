-- liquibase formatted sql

-- changeset feraun:14-change-user-pk-to-uuid

-- 1. Удаляем связи из зависимых таблиц
ALTER TABLE orders DROP CONSTRAINT fk_order_user;

-- 2. МИГРАЦИЯ ДАННЫХ: Добавляем временную колонку в заказы
ALTER TABLE orders ADD COLUMN new_user_id UUID;

-- 3. Копируем правильные UUID из таблицы users в таблицу заказов
UPDATE orders SET new_user_id = users.keycloak_id FROM users WHERE orders.user_id = users.id;

-- 4. Заменяем старую колонку на новую в заказах
ALTER TABLE orders DROP COLUMN user_id;
ALTER TABLE orders RENAME COLUMN new_user_id TO user_id;
ALTER TABLE orders ALTER COLUMN user_id SET NOT NULL;

-- 5. Меняем тип Primary Key в таблице users
ALTER TABLE users
ALTER COLUMN id DROP DEFAULT,
ALTER COLUMN id SET DATA TYPE UUID USING (keycloak_id::uuid);

-- 6. Удаляем старую колонку keycloak_id
ALTER TABLE users DROP COLUMN IF EXISTS keycloak_id;

-- 7. Возвращаем внешний ключ (теперь ID в заказах точно совпадают с ID в юзерах)
ALTER TABLE orders
ADD CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id);