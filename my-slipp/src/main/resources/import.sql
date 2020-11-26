INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (1, 'javajigi', 'test', 'name1', 'javajigi@slipp.net' );
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES (2, 'sanjigi', 'test', 'name2', 'sanjigi@slipp.net' );

INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES(1, 1, '국내에서 Ruby on Rails', 'Ruby on Rails는', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES(2, 2, '산지기가 쓴 글', '산지기가 뭐지', CURRENT_TIMESTAMP());