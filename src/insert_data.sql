/*
TRUNCATE TABLE types CASCADE;
TRUNCATE TABLE roles CASCADE;
TRUNCATE TABLE status CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE clients CASCADE;
TRUNCATE TABLE trainers CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE gymmemberships CASCADE;
TRUNCATE TABLE orderinfo CASCADE;
TRUNCATE TABLE prescriptions CASCADE;
*/


INSERT INTO types (name, discount)
VALUES ('NEW', '0'),
('REGULAR', '10'),
('CORPORATE', '15');

INSERT INTO roles (name)
VALUES ('ADMIN'),
('CLIENT'),
('TRAINER');

INSERT INTO status (name)
VALUES ('PENDING'),
('CONFIRM');

INSERT INTO users (email, password, role_id)
VALUES ('admin@gmail.com', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', (SELECT id FROM roles WHERE name = 'ADMIN'));

INSERT INTO gymmemberships (number_of_visits, type_of_training, cost)
VALUES (1, 'crossfit', 5),
(5, 'crossfit', 27),
(1, 'pilates', 6),
(7, 'crossfit', 40),
(1, 'gym', 4),
(1, 'fitness', 5),
(5, 'fitness', 25),
(10, 'fitness', 45);

