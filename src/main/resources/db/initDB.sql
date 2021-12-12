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

create schema if not exists cl;

create sequence if not exists cl.bank_book_id_seq;

create table if not exists cl.bank_book (
     id integer unique not null default nextval('cl.bank_book_id_seq'),
     user_id integer not null,
     number varchar not null,
     amount decimal not null,
     currency varchar not null,
     PRIMARY KEY (id)
);
