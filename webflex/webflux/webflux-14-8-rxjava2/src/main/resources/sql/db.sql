create database book;

use book;

create table wallet
(
 id int auto_increment primary key,
 owner varchar(255) not null,
 balance int not null,
 deposits int not null,
 withdraws int not null
);

create table book (
 id varchar(64) primary key,
 title varchar(255) not null,
 publishing_year integer not null
);

insert into book values('98cfd43e-1967-47a1-ace7-8399a29866a0','The Martian', 2011);
insert into book values('99cfd43e-1111-3333-ace7-8399a29866a0','Blue Mars', 1996);
insert into book values('11111111-1967-2343-7777-000000000000','The Case for Mars', 1996);
insert into book values('99999999-1967-47a1-aaaa-8399a29866a0','The War of Worlds', 1897);
insert into book values('99cfd43e-1967-3344-e34a-222222233333','Edison''s Conquest of Mars', 1947);
