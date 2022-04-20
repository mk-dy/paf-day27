drop database if exists mart;

create database mart;

use mart;

create table po (
    ord_id int auto_increment not null,
    name varchar(64) not null,
    email varchar(64) not null,
    primary key(ord_id)
);

create table line_item (
    item_id int auto_increment not null,
    description varchar(64),
    quantity int,
    unit_price decimal (14,4),
    ord_id int, -- this is the foreign key to the po table
    primary key(item_id),
    constraint fk_ord_id
        foreign key(ord_id)
        references po(ord_id)
);