--liquibase formatted sql

--changeset emelnikov:prepare
create table users (
    id int primary key,
    login varchar(255),
    password varchar(255),
    created timestamp
)
/