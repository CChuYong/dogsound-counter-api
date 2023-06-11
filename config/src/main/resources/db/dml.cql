INSERT INTO test.user (user_id, created_at, email, nickname) VALUES ('test', '2023-06-06 05:24:29.902+0000', 'test@bearus.co', '테스트용');
INSERT INTO test.room (room_id, created_at, owner_id, room_nm) VALUES ('test', '2023-06-06 05:24:29.902+0000', 'test', '테스트방');
INSERT INTO test.room_user (room_user_id, room_id, user_id, nickname, invited_by, last_read_message_id, created_at)
    VALUES ('test_1', 'test', 'test', '엄준식', 'test', NULL, '2023-06-06 05:24:29.902+0000');
INSERT INTO test.violent (violent_id, created_at, created_user_id, description, name, room_id, violent_price)
    VALUES ('test', '2023-06-06 05:24:29.902+0000', 'test', '테스트 위반', 'um', 'test', 20000);
INSERT INTO test.message (message_id, catcher_id, content, created_at, room_id, speaker_id, violent_id, violent_price)
    VALUES ('message_1', 'test', 'um 이라는 욕을?','2023-06-06 05:24:29.902+0000', 'test', 'test', 'test', 20000);
