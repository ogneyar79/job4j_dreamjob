CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT
);

CREATE TABLE photo(
   id SERIAL PRIMARY KEY
);


ALTER TABLE candidate ADD COLUMN photoId INTEGER REFERENCES photo(id) ON DELETE SET NULL;

ALTER TABLE candidate DROP COLUMN photoId;

ALTER TABLE photo ADD COLUMN name TEXT;

create table users
(
    id       serial      not null
        constraint user_pkey
            primary key,
    name     text,
    email    varchar     not null
        constraint user_email_key
            unique,
    password varchar(80) not null
        constraint user_password_key
            unique
);
create table city (
id SERIAL PRIMARY KEY,
name TEXT not null
);
ALTER TABLE candidate ADD COLUMN cityId INTEGER REFERENCES city(id);
INSERT into city (name) values('Рим'),('Москва'),('Тула'),('Париж'),('Бангкок'),('Юрмала'),('Вильнус'),('Берлин'),('Барселона'),('Мадрид'),('Афины');

