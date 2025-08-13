INSERT INTO groups (character_id, invite_code)
VALUES (1, 'INVITE123'),
       (2, 'INVITE456'),
       (3, 'INVITE789'),
       (4, 'INVITE101'),
       (5, 'INVITE112');

INSERT INTO character (name, image_url)
VALUES ('알로', 'https://fake.com/1.png'),
       ('깨끗이', 'https://fake.com/2.png'),
       ('코클린', 'https://fake.com/3.png'),
       ('깔끔이', 'https://fake.com/4.png'),
       ('쓱싹이', 'https://fake.com/5.png');

INSERT INTO member (code, name, profile_image_url)
VALUES ('M001', '멤버1', 'https://fake.com/m1.png'),
       ('M002', '멤버2', 'https://fake.com/m2.png'),
       ('M003', '멤버3', 'https://fake.com/m3.png'),
       ('M004', '멤버4', 'https://fake.com/m4.png'),
       ('M005', '멤버5', 'https://fake.com/m5.png'),
       ('M006', '멤버6', 'https://fake.com/m6.png'),
       ('M007', '멤버7', 'https://fake.com/m7.png'),
       ('M008', '멤버8', 'https://fake.com/m8.png'),
       ('M009', '멤버9', 'https://fake.com/m9.png'),
       ('M010', '멤버10', 'https://fake.com/m10.png');

INSERT INTO group_member (group_id, member_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 5),
       (2, 6),
       (2, 7),
       (3, 8),
       (3, 9),
       (3, 10),
       (4, 1),
       (4, 5);

INSERT INTO place (name, group_id)
VALUES ('거실', 1),
       ('주방', 1),
       ('침실', 1),
       ('욕실', 2),
       ('발코니', 2),
       ('거실', 3),
       ('서재', 3),
       ('화장실', 4),
       ('현관', 4);

INSERT INTO tag (name, group_id)
VALUES ('깨끗이', 1),
       ('빡빡', 1),
       ('쓱쓱', 2),
       ('열심히', 2),
       ('섬세하게', 3),
       ('크린토피아처럼', 3),
       ('매우열심히', 4);

INSERT INTO house_work (name, place_id, group_id, task_date, notified, completed)
VALUES ('세탁기 돌리기', 4, 2, '2025-08-10', false, true),
       ('바닥 청소', 1, 1, '2025-08-10', false, true),
       ('쓰레기 버리기', 2, 1, '2025-08-10', true, false),
       ('설거지', 2, 1, '2025-08-10', false, true),
       ('욕실 청소', 3, 2, '2025-08-10', true, false),

       ('세탁기 돌리기', 4, 2, '2025-08-11', false, true),
       ('창문 닦기', 1, 1, '2025-08-11', false, false),
       ('책상 정리', 2, 1, '2025-08-11', true, true),
       ('현관 청소', 4, 2, '2025-08-11', false, true),

       ('빨래 널기', 4, 2, '2025-08-12', false, false),
       ('화장실 청소', 3, 3, '2025-08-12', false, true),
       ('방 청소', 3, 2, '2025-08-12', true, true),
       ('카펫 청소', 4, 3, '2025-08-12', true, false),

       ('쓰레기 분리수거', 1, 1, '2025-08-13', false, false),
       ('책장 정리', 2, 1, '2025-08-13', true, false),
       ('주방 정리', 2, 3, '2025-08-13', false, true),
       ('침대 정리', 5, 1, '2025-08-13', false, false),

       ('발코니 청소', 6, 2, '2025-08-14', true, true),
       ('책상 청소', 7, 3, '2025-08-14', false, false),
       ('서랍 정리', 7, 3, '2025-08-14', true, false),
       ('화장대 정리', 8, 4, '2025-08-14', false, true),

       ('현관 닦기', 9, 4, '2025-08-15', true, false),
       ('주방 청소', 2, 1, '2025-08-15', false, false),
       ('거실 정리', 1, 1, '2025-08-15', true, true),
       ('세탁기 청소', 4, 2, '2025-08-15', false, false),

       ('침실 정리', 5, 1, '2025-08-16', true, true),
       ('책장 청소', 7, 3, '2025-08-16', false, false),
       ('발코니 정리', 6, 2, '2025-08-16', true, false),
       ('거실 청소', 1, 1, '2025-08-16', false, true),

       ('욕실 정리', 3, 2, '2025-08-17', true, false),
       ('바닥 닦기', 1, 1, '2025-08-17', false, true),
       ('책상 정리', 2, 3, '2025-08-17', true, false),
       ('주방 정리', 2, 3, '2025-08-17', false, true),

       ('세탁물 개기', 4, 2, '2025-08-18', false, true),
       ('책장 정리', 7, 3, '2025-08-18', true, false),
       ('화장대 청소', 8, 4, '2025-08-18', false, true),
       ('침대 청소', 5, 1, '2025-08-18', true, false),

       ('현관 청소', 9, 4, '2025-08-19', false, true),
       ('거실 청소', 1, 1, '2025-08-19', true, false),
       ('주방 청소', 2, 1, '2025-08-19', false, true),
       ('발코니 청소', 6, 2, '2025-08-19', true, false),

       ('방 정리', 3, 2, '2025-08-20', false, true),
       ('책상 청소', 7, 3, '2025-08-20', true, false),
       ('서랍 정리', 7, 3, '2025-08-20', false, true),
       ('화장대 정리', 8, 4, '2025-08-20', true, false),

       ('현관 닦기', 9, 4, '2025-08-21', false, true),
       ('거실 정리', 1, 1, '2025-08-21', true, false),
       ('주방 정리', 2, 3, '2025-08-21', false, true),
       ('세탁기 점검', 4, 2, '2025-08-21', true, false),

       ('침대 정리', 5, 1, '2025-08-22', false, true),
       ('발코니 청소', 6, 2, '2025-08-22', true, false),
       ('책장 정리', 7, 3, '2025-08-22', false, true),
       ('화장대 정리', 8, 4, '2025-08-22', true, false),

       ('현관 청소', 9, 4, '2025-08-23', false, true),
       ('거실 청소', 1, 1, '2025-08-23', true, false),
       ('주방 청소', 2, 1, '2025-08-23', false, true),
       ('발코니 정리', 6, 2, '2025-08-23', true, false),

       ('방 청소2', 3, 2, '2025-08-24', false, true),
       ('책상 청소3', 7, 3, '2025-08-24', true, false),
       ('서랍 정리3', 7, 3, '2025-08-24', false, true),
       ('화장대 청소2', 8, 4, '2025-08-24', true, false),

       ('현관 닦기3', 9, 4, '2025-08-25', false, true),
       ('거실 청소4', 1, 1, '2025-08-25', true, false),
       ('주방 정리4', 2, 3, '2025-08-25', false, true),
       ('세탁물 개기2', 4, 2, '2025-08-25', true, false),

       ('침대 정리', 5, 1, '2025-08-26', false, true),
       ('발코니 청소', 6, 2, '2025-08-26', true, false),
       ('책장 정리', 7, 3, '2025-08-26', false, true),
       ('화장대 정리', 8, 4, '2025-08-26', true, false),

       ('현관 청소', 9, 4, '2025-08-27', false, true),
       ('거실 청소', 1, 1, '2025-08-27', true, false),
       ('주방 청소', 2, 1, '2025-08-27', false, true),
       ('발코니 정리', 6, 2, '2025-08-27', true, false),

       ('방 청소', 3, 2, '2025-08-28', false, true),
       ('책상 청소', 7, 3, '2025-08-28', true, false),
       ('서랍 정리', 7, 3, '2025-08-28', false, true),
       ('화장대 청소', 8, 4, '2025-08-28', true, false),

       ('현관 닦기', 9, 4, '2025-08-29', false, true),
       ('거실 청소', 1, 1, '2025-08-29', true, false),
       ('주방 정리', 2, 3, '2025-08-29', false, true),
       ('세탁물 개기', 4, 2, '2025-08-29', true, false);

INSERT INTO house_work_member (house_work_id, member_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (3, 2),
       (4, 3),
       (6, 2),
       (7, 2),
       (7, 3),
       (8, 3),
       (8, 4),
       (9, 4),
       (9, 5),
       (10, 1),
       (10, 3),
       (11, 2),
       (11, 5),
       (12, 1),
       (12, 4),
       (13, 3),
       (13, 5),
       (14, 2),
       (14, 4),
       (15, 1),
       (15, 5),
       (16, 6),
       (16, 7),
       (17, 6),
       (17, 7),
       (18, 8),
       (18, 9),
       (19, 9),
       (19, 10),
       (20, 1),
       (20, 5);

INSERT INTO preset_house_work_category (name)
VALUES ('임시1'),
       ('임시2');

INSERT INTO preset_house_work (preset_house_work_category_id, name)
VALUES (1, '임시1'),
       (1, '임시2'),
       (2, '임시3');

INSERT INTO preset_place (name)
VALUES ('임시1'),
       ('임시2');

INSERT INTO preset_tag (name)
VALUES ('임시1'),
       ('임시2');

INSERT INTO house_work_tag (house_work_id, tag_id)
VALUES (1, 1),
       (3, 2),
       (5, 3);