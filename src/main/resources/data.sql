INSERT INTO house_work_group (character_id, invite_code)
VALUES (1, 'INVITE123'),
       (2, 'INVITE456'),
       (3, 'INVITE789'),
       (4, 'INVITE101'),
       (5, 'INVITE112');

INSERT INTO house_work_character (name, image_url)
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

INSERT INTO group_member (house_work_group_id, member_id)
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

INSERT INTO place (name, house_work_group_id)
VALUES ('거실', 1),
       ('주방', 1),
       ('침실', 1),
       ('욕실', 2),
       ('발코니', 2),
       ('거실', 3),
       ('서재', 3),
       ('화장실', 4),
       ('현관', 4);

INSERT INTO tag (name, house_work_group_id)
VALUES ('깨끗이', 1),
       ('빡빡', 1),
       ('쓱쓱', 2),
       ('열심히', 2),
       ('섬세하게', 3),
       ('크린토피아처럼', 3),
       ('매우열심히', 4);

INSERT INTO house_work (name, place_id, house_work_group_id, task_date, notified, completed, completed_date)
VALUES ('세탁기 돌리기', 4, 1, '2025-08-10', false, true, '2025-08-10'),
       ('바닥 청소', 1, 1, '2025-08-10', false, true, '2025-08-10'),
       ('쓰레기 버리기', 2, 1, '2025-08-10', true, false, null),
       ('설거지', 2, 1, '2025-08-10', false, true, '2025-08-10'),
       ('창문 닦기', 1, 1, '2025-08-11', false, false, null),
       ('책상 정리', 2, 1, '2025-08-11', true, true, '2025-08-11'),
       ('쓰레기 분리수거', 1, 1, '2025-08-13', false, false, null),
       ('책장 정리', 2, 1, '2025-08-13', true, false, null),
       ('침대 정리', 5, 1, '2025-08-13', false, false, null),
       ('주방 청소', 2, 1, '2025-08-15', false, false, null),
       ('거실 정리', 1, 1, '2025-08-15', true, true, '2025-08-15'),
       ('침실 정리', 5, 1, '2025-08-16', true, true, '2025-08-16'),
       ('거실 바닥 청소', 1, 1, '2025-08-16', false, true, '2025-08-16'),
       ('바닥 물청소', 1, 1, '2025-08-17', false, true, '2025-08-15'),
       ('침대 시트 교체', 5, 1, '2025-08-18', true, false, null),
       ('거실 먼지 제거', 1, 1, '2025-08-19', true, false, null),
       ('주방 싱크대 닦기', 2, 1, '2025-08-19', false, true, '2025-08-19'),
       ('침대 정리 및 정돈', 5, 1, '2025-08-22', false, true, '2025-08-22'),
       ('현관 매트 청소', 9, 1, '2025-08-23', false, true, '2025-08-23'),
       ('거실 소품 정리', 1, 1, '2025-08-23', true, false, null),
       ('주방 조리대 정리', 2, 1, '2025-08-23', false, true, '2025-08-23'),
       ('침대 매트 청소', 5, 1, '2025-08-26', false, true, '2025-08-26'),
       ('현관 신발 정리', 9, 1, '2025-08-27', false, true, '2025-08-27'),
       ('거실 장식품 정리', 1, 1, '2025-08-27', true, false, null),
       ('주방 찌든때 제거', 2, 1, '2025-08-27', false, true, '2025-08-27'),
       ('거실 카펫 청소', 1, 1, '2025-08-29', true, false, null),

       ('욕실 청소', 3, 2, '2025-08-10', true, false, null),
       ('세탁기 돌리기', 4, 2, '2025-08-11', false, true, '2025-08-11'),
       ('현관 청소', 4, 2, '2025-08-11', false, true, '2025-08-11'),
       ('빨래 널기', 4, 2, '2025-08-12', false, false, null),
       ('방 청소', 3, 2, '2025-08-12', true, true, '2025-08-12'),
       ('발코니 청소', 6, 2, '2025-08-14', true, true, '2025-08-14'),
       ('세탁기 청소', 4, 2, '2025-08-15', false, false, null),
       ('발코니 정리', 6, 2, '2025-08-16', true, false, null),
       ('욕실 정리', 3, 2, '2025-08-17', true, false, null),
       ('세탁물 개기', 4, 2, '2025-08-18', false, true, '2025-08-18'),
       ('발코니 청소', 6, 2, '2025-08-19', true, false, null),
       ('방 정리', 3, 2, '2025-08-20', false, true, '2025-08-20'),
       ('현관 닦기', 9, 2, '2025-08-21', false, true, '2025-08-21'),
       ('세탁기 점검', 4, 2, '2025-08-21', true, false, null),
       ('발코니 청소', 6, 2, '2025-08-22', true, false, null),
       ('발코니 정리', 6, 2, '2025-08-23', true, false, null),
       ('방 청소', 3, 2, '2025-08-28', false, true, '2025-08-28'),

       ('화장실 청소', 3, 3, '2025-08-12', false, true, '2025-08-12'),
       ('카펫 청소', 4, 3, '2025-08-12', true, false, null),
       ('주방 정리', 2, 3, '2025-08-13', false, true, '2025-08-13'),
       ('책상 청소', 7, 3, '2025-08-14', false, false, null),
       ('서랍 정리', 7, 3, '2025-08-14', true, false, null),
       ('책장 청소', 7, 3, '2025-08-16', false, false, null),
       ('책상 정리', 2, 3, '2025-08-17', true, false, null),
       ('주방 정리', 2, 3, '2025-08-17', false, true, '2025-08-17'),
       ('책장 정리', 7, 3, '2025-08-18', true, false, null),
       ('책상 청소', 7, 3, '2025-08-20', true, false, null),
       ('서랍 정리', 7, 3, '2025-08-20', false, true, '2025-08-20'),
       ('책장 정리', 7, 3, '2025-08-22', false, true, '2025-08-22'),
       ('책장 정리', 7, 3, '2025-08-26', false, true, '2025-08-26'),
       ('책상 청소', 7, 3, '2025-08-28', true, false, null),
       ('서랍 정리', 7, 3, '2025-08-28', false, true, '2025-08-28'),
       ('책상 청소', 7, 3, '2025-08-24', true, false, null),
       ('서랍 정리', 7, 3, '2025-08-24', false, true, '2025-08-24'),

       ('화장대 정리', 8, 4, '2025-08-14', false, true, '2025-08-14'),
       ('현관 닦기', 9, 4, '2025-08-15', true, false, null),
       ('화장대 청소', 8, 4, '2025-08-18', false, true, '2025-08-18'),
       ('현관 청소', 9, 4, '2025-08-19', false, true, '2025-08-19'),
       ('화장대 정리', 8, 4, '2025-08-20', true, false, null),
       ('현관 닦기', 9, 4, '2025-08-21', false, true, '2025-08-21'),
       ('화장대 정리', 8, 4, '2025-08-22', true, false, null),
       ('현관 청소', 9, 4, '2025-08-23', false, true, '2025-08-23'),
       ('발코니 정리', 6, 4, '2025-08-23', true, false, null),
       ('화장대 청소', 8, 4, '2025-08-24', true, false, null),
       ('현관 닦기', 9, 4, '2025-08-25', false, true, '2025-08-25'),
       ('화장대 정리', 8, 4, '2025-08-26', true, false, null);

INSERT INTO house_work_member (house_work_id, member_id)
VALUES -- memberId = 1
       (1, 1),
       (3, 1),
       (4, 1),
       (7, 1),
       (14, 1),
       (15, 1),
       (18, 1),
       (19, 1),
       (1, 2),
       (3, 2),
       (4, 2),
       (1, 3),
       (3, 3),
       (4, 3),
       (7, 3),
       (8, 4),
       (6, 5),
       (9, 5),
       (10, 5),
       (12, 5),
       (16, 5),
       (18, 5),
       (19, 5),
       (9, 6),
       (10, 6),
       (16, 6),
       (12, 7),
       (11, 8),
       (13, 8),
       (17, 8),
       (20, 8),
       (11, 9),
       (13, 9),
       (17, 10),
       (20, 10);

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

INSERT INTO emotion_card (sender_id, receiver_id, house_work_id, disappointment, created_date)
VALUES (1, 2, 3, '조금 더 신경 써주세요', '2025-08-14T10:30:00'),
       (1, 3, 4, '다음엔 더 깨끗하게 부탁해요', '2025-08-14T10:32:15'),
       (1, 4, 8, '시간 약속을 지켜주세요', '2025-08-14T10:35:45');

INSERT INTO compliment (emotion_card_id, content)
VALUES (1, '정성껏 해줘서 고마워요'),
       (1, '깔끔하게 마무리했네요'),
       (2, '최대한 하려고 노력했네요'),
       (2, '세심하게 해줘서 좋았어요'),
       (3, '결과물은 훌륭했어요'),
       (3, '고생했어요!');