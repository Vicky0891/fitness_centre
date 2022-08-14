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
('CORPORATE', '15');

INSERT INTO roles (name)
VALUES ('ADMIN'),
('CLIENT'),
('TRAINER');

INSERT INTO status (name)
VALUES ('PENDING'),
('CONFIRM');

INSERT INTO users (email, password, role_id)
VALUES ('ant@gmail.com', 'dsfhih1542', (SELECT id FROM roles WHERE name = 'CLIENT')),
('valera@mail.com', 'valera', (SELECT id FROM roles WHERE name = 'TRAINER'));

INSERT INTO clients (user_id, first_name, last_name, birth_date, phone_number, type_id, trainer_id, additional_info)
VALUES (3, 'Anton', 'Petrov', '1995-05-20', '+375296544565', (SELECT id FROM types WHERE name = 'NEW'), 4, 'no contraindications');

INSERT INTO trainers (user_id, first_name, last_name, birth_date, category)
VALUES (4, 'Valeria', 'Reshenik', '1989-05-15', 'high');

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
VALUES (2, 10, 1, 27);

INSERT INTO prescriptions (trainer_id, client_id, type_of_training, equipment, diet, status_id)
VALUES (4, 3, 'crossfit', 'ribbons', 'ordinary', (SELECT id FROM status WHERE name = 'PENDING'));

