CREATE SCHEMA kailua_car_rental;

CREATE TABLE IF NOT EXISTS car
(car_id INT NOT NULL AUTO_INCREMENT,
car_brand CHAR(30) NOT NULL,
car_model CHAR(30) NOT NULL,
car_fuel CHAR (10) NOT NULL,
car_plate CHAR (6) NOT NULL,
car_reg_year SMALLINT NOT NULL,
car_reg_month SMALLINT NOT NULL,
car_odometer INT NOT NULL, 
car_status ENUM ('AVAILABLE', 'RENTED') NOT NULL DEFAULT 'AVAILABLE',
PRIMARY KEY (car_id)
);

CREATE TABLE IF NOT EXISTS city
(
city_id INT NOT NULL AUTO_INCREMENT,
city_name CHAR(30),
zip_code CHAR(20),
PRIMARY KEY (city_id)
);

CREATE TABLE IF NOT EXISTS customer
(customer_id INT NOT NULL AUTO_INCREMENT,
name CHAR(100) NOT NULL,
phone_no CHAR(20),
email CHAR (60),
licence_no CHAR(50) NOT NULL UNIQUE,
city_id INT NOT NULL,
driver_since DATE NOT NULL, 
FOREIGN KEY (city_id) REFERENCES city(city_id),
PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS rental
(
rental_id INT NOT NULL AUTO_INCREMENT,
customer_id INT NOT NULL,
car_id INT NOT NULL,
start_date DATE NOT NULL,
end_date DATE,
rental_status ENUM('ACTIVE','COMPLETED') DEFAULT 'ACTIVE',
FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
FOREIGN KEY(car_id) REFERENCES car(car_id),
PRIMARY KEY (rental_id)
);

