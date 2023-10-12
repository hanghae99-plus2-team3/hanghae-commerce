INSERT INTO auth_member_entity(pk, login_id, pw, created_time)
VALUES (1, 'jay', '$2a$07$FsA4nEjwGG/aPnhpIaJQS.oVKIG/F6QGmiU00n7fZgldwRs9iOEQy', '2023-10-06T16:43:47.970');

INSERT INTO auth_token_entity(pk,token,member_pk, created_time)
VALUES (1, '1','1', '2023-10-06T16:43:47.970')
