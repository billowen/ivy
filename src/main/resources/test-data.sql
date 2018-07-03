INSERT INTO stories (title, dateuploaded) VALUES ('test', now());
INSERT INTO stories (title, dateuploaded) VALUES ('test2', now());

INSERT INTO comments(story_id, content, date_update) VALUES (1, 'test', now());
INSERT INTO comments(story_id, content, date_update) VALUES (1, 'test2', now());
INSERT INTO comments(story_id, content, date_update) VALUES (2, 'test3', now());

