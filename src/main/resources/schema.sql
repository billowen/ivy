DROP TABLE IF EXISTS stories;
CREATE TABLE stories (
  id bigint auto_increment,
  bytes longblob,
  title varchar(100),
  tag varchar(50),
  dateuploaded timestamp,
  primary key (id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
  id bigint auto_increment,
  story_id bigint,
  content varchar(1000),
  date_update timestamp,
  primary key (id)
);