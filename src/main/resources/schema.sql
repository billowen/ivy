DROP TABLE IF EXISTS stories;
CREATE TABLE IF NOT EXISTS stories (
  id bigint auto_increment,
  bytes blob,
  title varchar(100),
  content varchar(10000),
  dateuploaded timestamp,
  primary key (id)
);