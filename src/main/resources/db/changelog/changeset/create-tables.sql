-- liquibase formatted sql

-- changeset feraun:1-create-passwords

CREATE TABLE passwords (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- changeset feraun:2-create-users

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    contact_number VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP,
    role VARCHAR(50) NOT NULL,
    password_id BIGINT NOT NULL,
    institution_id BIGINT,

    CONSTRAINT fk_user_password
        FOREIGN KEY (password_id)
        REFERENCES passwords(id)
        ON DELETE CASCADE
);

-- changeset feraun:3-create-images

CREATE TABLE images (
    id UUID PRIMARY KEY,
    size BIGINT NOT NULL,
    http_content_type VARCHAR(255)
);

-- changeset feraun:4-create-categories

CREATE TABLE categories_of_institution (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500)
);

-- changeset feraun:5-create-cities

CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    region VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);

-- changeset feraun:6-create-institutions

CREATE TABLE institutions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    user_id BIGINT NOT NULL,
    city_id BIGINT NOT NULL,
    address VARCHAR(255),
    rating DOUBLE PRECISION,
    contact_number VARCHAR(255),
    created_at TIMESTAMP,
    image_id UUID NOT NULL,

    CONSTRAINT fk_institution_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_institution_city
        FOREIGN KEY (city_id)
        REFERENCES cities(id),

    CONSTRAINT fk_institution_logo
        FOREIGN KEY (image_id)
        REFERENCES images(id)
);


-- changeset feraun:7-create-institution-categories

CREATE TABLE institution_categories (
    institution_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    PRIMARY KEY (institution_id, category_id),

    CONSTRAINT fk_ic_institution
        FOREIGN KEY (institution_id)
        REFERENCES institutions(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ic_category
        FOREIGN KEY (category_id)
        REFERENCES categories_of_institution(id)
        ON DELETE CASCADE
);

-- changeset feraun:8-create-boxes

CREATE TABLE boxes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE PRECISION NOT NULL,
    randomly BOOLEAN,
    quantity INTEGER NOT NULL,
    institution_id BIGINT NOT NULL,

    CONSTRAINT fk_box_institution
        FOREIGN KEY (institution_id)
        REFERENCES institutions(id)
        ON DELETE CASCADE
);

-- changeset feraun:9-create-orders

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    box_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    amount DOUBLE PRECISION,
    created_at TIMESTAMP,

    CONSTRAINT fk_order_box
        FOREIGN KEY (box_id)
        REFERENCES boxes(id),

    CONSTRAINT fk_order_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

-- changeset feraun:10-add-user-institution-fk

ALTER TABLE users
ADD CONSTRAINT fk_user_institution
FOREIGN KEY (institution_id)
REFERENCES institutions(id);
