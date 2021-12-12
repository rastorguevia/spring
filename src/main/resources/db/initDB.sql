create schema if not exists dict;

create sequence if not exists dict.currency_id_seq;

create table if not exists dict.currency (
    id integer unique not null default nextval('dict.currency_id_seq'),
    name varchar not null,
    PRIMARY KEY (id)
);

insert into dict.currency (name) values ('RUB');
insert into dict.currency (name) values ('USD');
insert into dict.currency (name) values ('EUR');