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

INSERT INTO users (email, password, first_name, last_name, birth_date, phone_number, additional_info, type_id, role_id)
VALUES ('ant@gmail.com', 'dsfhih1542', 'Anton', 'Petrov', '1995-05-20', '+375296544565', 'no contraindications', (SELECT id FROM types WHERE name = 'NEW'), (SELECT id FROM roles WHERE name = 'CLIENT')),
('sorok40@gmail.com', '45487sefsdfdsf', 'Dmitry', 'Sorokin', '1990-04-15', '+375291112233', 'no contraindications', (SELECT id FROM types WHERE name = 'REGULAR'), (SELECT id FROM roles WHERE name = 'CLIENT')),
('ritylja256@mail.com', '4564fdg6f4', 'Rita', 'Lopatina', '1999-10-12', '+375291256598', 'joint problems', (SELECT id FROM types WHERE name = 'CORPORATE'), (SELECT id FROM roles WHERE name = 'CLIENT')),
('admin@mail.com', 'admin', 'Admin', 'Adminov', '1995-11-12', '+375291111111', 'no', (SELECT id FROM types WHERE name = 'NEW'), (SELECT id FROM roles WHERE name = 'ADMIN')),
('valera@mail.com', 'valera', 'Valeria', 'Reshenik', '1989-05-15', '+37529956545', 'no', (SELECT id FROM types WHERE name = 'NEW'), (SELECT id FROM roles WHERE name = 'TRAINER')),
('jana@mail.com', 'janajana', 'Jana', 'Vashkevich', '1990-03-04', '+375335645689', 'no', (SELECT id FROM types WHERE name = 'NEW'), (SELECT id FROM roles WHERE name = 'TRAINER'));

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

