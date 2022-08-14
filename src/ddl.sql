/*

/*

DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS orderinfo;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS gymmemberships;

*/

CREATE TABLE IF NOT EXISTS types (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(10) UNIQUE NOT NULL,
discount BIGINT NOT NULL);

CREATE TABLE IF NOT EXISTS roles (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(10) UNIQUE NOT NULL);

CREATE TABLE IF NOT EXISTS status (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(10) UNIQUE NOT NULL);

CREATE TABLE IF NOT EXISTS users (
id BIGSERIAL PRIMARY KEY,
email VARCHAR(30) NOT NULL,
password text NOT NULL,
role_id BIGINT NOT NULL REFERENCES roles,
deleted BOOLEAN NOT NULL DEFAULT FALSE);

CREATE TABLE IF NOT EXISTS trainers (
user_id BIGINT NOT NULL REFERENCES users,
first_name text,
last_name text,
birth_date date,
category text);

CREATE TABLE IF NOT EXISTS clients (
user_id BIGINT NOT NULL REFERENCES users,
first_name text,
last_name text,
birth_date date,
phone_number VARCHAR(13),
type_id BIGINT REFERENCES types,
trainer_id BIGINT,
additional_info text);

CREATE TABLE IF NOT EXISTS orders (
id BIGSERIAL PRIMARY KEY,
date_of_order date NOT NULL,
user_id BIGINT NOT NULL,
total_cost DECIMAL NOT NULL,
status_id BIGINT NOT NULL REFERENCES status,
feedback text,
deleted BOOLEAN NOT NULL DEFAULT FALSE);

CREATE TABLE IF NOT EXISTS gymmemberships (
id BIGSERIAL PRIMARY KEY,
number_of_visits BIGINT NOT NULL,
type_of_training text NOT NULL,
cost DECIMAL NOT NULL,
deleted BOOLEAN NOT NULL DEFAULT FALSE);

CREATE TABLE IF NOT EXISTS orderinfo (
id BIGSERIAL PRIMARY KEY,
order_id BIGINT NOT NULL REFERENCES orders,
gymmembership_id BIGINT NOT NULL REFERENCES gymmemberships,
gymmembership_quantity BIGINT NOT NULL,
gymmembership_price BIGINT NOT NULL);

CREATE TABLE IF NOT EXISTS prescriptions (
id BIGSERIAL PRIMARY KEY,
trainer_id BIGINT NOT NULL REFERENCES users,
client_id BIGINT NOT NULL REFERENCES users,
type_of_training text,
equipment text,
diet text,
status_id BIGINT NOT NULL REFERENCES status,
deleted BOOLEAN NOT NULL DEFAULT FALSE);
