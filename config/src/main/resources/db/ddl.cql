DROP
KEYSPACE test;
CREATE
KEYSPACE test
            WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};
USE
test;

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
    PRIMARY KEY (room_id, message_id)
) WITH COMMENT = 'message table';

CREATE TABLE room
(
    room_id      text,
    room_nm      text,
    owner_id     text,
    room_img_url text,
    created_at   timestamp,
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
    user_id         text,
    email           text,
    nickname        text,
    tag             text,
    profile_img_url text,
    last_seen_at    timestamp,
    created_at      timestamp,
    PRIMARY KEY (user_id)
) WITH COMMENT = 'user table';

CREATE INDEX user_idx1 ON test.user (email);
CREATE INDEX user_idx2 ON test.user (nickname);
CREATE INDEX user_idx3 ON test.user (tag);


CREATE TABLE user_friend
(
    user_id_1  text,
    user_id_2  text,
    created_at timestamp,
    PRIMARY KEY (user_id_1, user_id_2)
) WITH COMMENT = 'user friend table';

CREATE TABLE user_friend_request
(
    from_user_id text,
    to_user_id   text,
    created_at   timestamp,
    PRIMARY KEY (from_user_id, to_user_id)
) WITH COMMENT = 'user friend request table';


CREATE TABLE user_notification_config
(
    user_id             text,
    social_alert        boolean,
    bad_sound_alert     boolean,
    non_bad_sound_alert boolean,
    notice_alert        boolean,
    PRIMARY KEY (user_id)
) WITH COMMENT = 'user notification table';

CREATE TABLE user_device
(
    user_id     text,
    fcm_token   text,
    device_info text,
    created_at  timestamp,
    PRIMARY KEY (user_id, fcm_token),
) WITH COMMENT = 'user device table';
CREATE INDEX user_device_idx1 ON test.user_device (user_id);


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
    room_user_id         text,
    room_id              text,
    user_id              text,
    nickname             text,
    invited_by           text,
    last_read_message_id text,
    created_at           timestamp,
    PRIMARY KEY (room_user_id)
) WITH COMMENT = 'room user table';
CREATE INDEX room_user_idx1 ON test.room_user (room_id);
CREATE INDEX room_user_idx2 ON test.room_user (user_id);

CREATE TABLE room_user_price
(
    room_user_id    text,
    start_day       text,
    cumulated_price counter,
    PRIMARY KEY (room_user_id, start_day)
) WITH COMMENT = 'room user price table';
