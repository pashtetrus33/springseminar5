-- Создаем таблицу с пользователями
create table if not exists users
(
    id   bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    status varchar(255) not null,
    role varchar(255) not null
);