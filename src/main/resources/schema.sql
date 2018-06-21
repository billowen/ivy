CREATE TABLE IF NOT EXISTS items (
id bigint auto_increment,
bytes blob,
description varchar(1000),
dateuploaded timestamp,
primary key (id)
);