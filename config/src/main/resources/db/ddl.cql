DROP KEYSPACE test;
CREATE KEYSPACE test
            WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE message
(
    message_id    text,
    room_id       text,
    content       text,
    violent_id    text,
    violent_price int,
    speaker_id    text,
    catcher_id    text,
    created_at    timestamp,
    PRIMARY KEY (message_id)
) WITH COMMENT = 'message table';

CREATE INDEX message_idx1 ON test.message (room_id);

CREATE TABLE room
(
    room_id    text,
    room_nm    text,
    owner_id   text,
    created_at timestamp,
    PRIMARY KEY (room_id)
) WITH COMMENT = 'room table';

CREATE TABLE user_social
(
    user_id      text,
    provider     text,
    provider_key text,
    created_at   timestamp,
    PRIMARY KEY (user_id)
) WITH COMMENT = 'user social table';

CREATE INDEX user_social_idx1 ON test.user_social (provider);
CREATE INDEX user_social_idx2 ON test.user_social (provider_key);

CREATE TABLE user
(
    user_id    text,
    email      text,
    nickname   text,
    created_at timestamp,
    PRIMARY KEY (user_id)
) WITH COMMENT = 'user table';

CREATE INDEX user_idx1 ON test.user (email);

CREATE TABLE violent
(
    violent_id      text,
    room_id         text,
    name            text,
    description     text,
    violent_price   int,
    created_user_id text,
    created_at      timestamp,
    PRIMARY KEY (violent_id)
) WITH COMMENT = 'user table';
CREATE INDEX violent_idx1 ON test.violent (room_id);

CREATE TABLE room_user
(
    room_user_id text,
    room_id      text,
    user_id      text,
    nickname     text,
    invited_by   text,
    last_read_message_id text,
    created_at   timestamp,
    PRIMARY KEY (room_user_id)
) WITH COMMENT = 'room user table';
CREATE INDEX room_user_idx1 ON test.room_user (room_id);
CREATE INDEX room_user_idx2 ON test.room_user (user_id);
