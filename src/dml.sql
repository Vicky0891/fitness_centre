/*
TRUNCATE TABLE types CASCADE;
TRUNCATE TABLE roles CASCADE;
TRUNCATE TABLE status CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE gymmemberships CASCADE;
TRUNCATE TABLE orderinfo CASCADE;
TRUNCATE TABLE prescriptions CASCADE;
*/



INSERT INTO types (name, discount)
VALUES ('NEW', '0'),
('REGULAR', '10'),
('CORPORATE', '15'),
('OTHER', '0');

INSERT INTO roles (name)
VALUES ('ADMIN'),
('CLIENT'),
('TRAINER');

INSERT INTO status (name)
VALUES ('PENDING'),
('CONFIRM');

INSERT INTO users (email, password, first_name, last_name, type_id, role_id, trainer_id)
VALUES ('ant@gmail.com', 'dsfhih1542', 'Anton', 'Petrov', (SELECT id FROM types WHERE name = 'NEW'), (SELECT id FROM roles WHERE name = 'CLIENT'), -1),
('sorok40@gmail.com', '45487sefsdfdsf', 'Dmitry', 'Sorokin', (SELECT id FROM types WHERE name = 'REGULAR'), (SELECT id FROM roles WHERE name = 'CLIENT'), 5),
('ritylja256@mail.com', '4564fdg6f4', 'Rita', 'Lopatina', (SELECT id FROM types WHERE name = 'CORPORATE'), (SELECT id FROM roles WHERE name = 'CLIENT'), 5),
('admin@mail.com', 'admin', 'Admin', 'Adminov', (SELECT id FROM types WHERE name = 'OTHER'), (SELECT id FROM roles WHERE name = 'ADMIN'), -2),
('valera@mail.com', 'valera', 'Valeria', 'Reshenik', (SELECT id FROM types WHERE name = 'OTHER'), (SELECT id FROM roles WHERE name = 'TRAINER'), -2),
('jana@mail.com', 'janajana', 'Jana', 'Vashkevich', (SELECT id FROM types WHERE name = 'OTHER'), (SELECT id FROM roles WHERE name = 'TRAINER'), -2);

INSERT INTO orders (date_of_order, user_id, total_cost, status_id, feedback)
VALUES ('2022-08-02', 2, 5, (SELECT id FROM status WHERE name = 'PENDING'), 'ok');

INSERT INTO gymmemberships (number_of_visits, type_of_training, cost)
VALUES (1, 'crossfit', 5),
(5, 'crossfit', 27),
(1, 'pilates', 6),
(7, 'crossfit', 40),
(1, 'gym', 4),
(1, 'fitness', 5),
(5, 'fitness', 25),
(10, 'fitness', 45);

INSERT INTO orderinfo (order_id, gymmembership_id, gymmembership_quantity, gymmembership_price)
VALUES (3, 17, 1, 5);

INSERT INTO prescriptions (trainer_id, user_id, type_of_training, equipment, diet, status_id)
VALUES (19, 16, 'crossfit', 'ribbons', 'ordinary', (SELECT id FROM status WHERE name = 'PENDING'));

