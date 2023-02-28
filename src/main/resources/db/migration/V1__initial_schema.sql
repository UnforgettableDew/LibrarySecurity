drop table if exists application_users;

create table application_users
(
    id       serial primary key,
    username varchar(32)  not null,
    password varchar(255) not null,
    role     varchar(255) not null,
    status   varchar(255) not null
);