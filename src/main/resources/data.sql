INSERT INTO USER (USER_ID, USERNAME, EMAIL, PASSWORD, NAME)
VALUES (1, 'admin', 'admin@naver.com','$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin');
INSERT INTO USER (USER_ID, USERNAME, EMAIL, PASSWORD, NAME)
VALUES (2, 'user','user@naver.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user');

INSERT INTO AUTHORITY (AUTHORITY_NAME)
values ('STUDENT');
INSERT INTO AUTHORITY (AUTHORITY_NAME)
values ('EDUCATOR');
INSERT INTO AUTHORITY (AUTHORITY_NAME)
values ('ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME)
values (1, 'STUDENT');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME)
values (1, 'ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME)
values (2, 'EDUCATOR');

INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (1,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (2,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (3,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (4,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (5,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (6,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (7,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (8,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (9,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (10,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (11,'hello','hello world','admin','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (12,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (13,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (14,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (15,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (16,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (17,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (18,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (19,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (20,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (21,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (22,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (23,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (24,'hello','hello world','user','develop',0,0);
INSERT INTO POST(POST_ID,TITLE,CONTENT,USERNAME,CATEGORY,LIKE_COUNT, HIT)
VALUES (25,'hello','hello world','user','develop',0,0);

INSERT INTO DICTIONARY(DICTIONARY_ID,WORD_ENGLISH,WORD_KOREAN,DESCRIPTION)
VALUES (1,'docker','도커','도커는 맛있다.');
INSERT INTO DICTIONARY(DICTIONARY_ID,WORD_ENGLISH,WORD_KOREAN,DESCRIPTION)
VALUES (2,'github','깃허브','응깃허브');

INSERT INTO COMMENT(COMMENT_ID,CONTENT,USERNAME)
VALUES (1,'안녕하세요구르트','admin')