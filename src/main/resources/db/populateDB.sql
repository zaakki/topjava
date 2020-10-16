DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);
INSERT INTO meals (user_id,date_time,description,calories)
VALUES (100000,'2020-01-29 10:00:00', 'Breakfast', 500),
       (100000,'2020-01-29 14:00:00', 'Lunch', 100),
       (100000,'2020-01-29 20:00:00', 'Dinner', 500),
       (100000,'2020-01-30 00:10:00', 'Border feast', 50),
       (100000,'2020-01-30 10:00:00', 'Breakfast', 300),
       (100000,'2020-01-30 14:00:00', 'Lunch', 900),
       (100000,'2020-01-30 19:00:00', 'Dinner', 800),
       (100001,'2020-01-30 10:00:00', 'Admin breakfast', 600),
       (100001,'2020-01-30 14:00:00', 'Admin lunch', 1500);

