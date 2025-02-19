CREATE SCHEMA kailua_car_rental;

CREATE TABLE IF NOT EXISTS car (
    car_id INT NOT NULL AUTO_INCREMENT,           
    car_brand CHAR(30) NOT NULL,                  
    car_model CHAR(30) NOT NULL,                  
    car_plate CHAR(6) NOT NULL,                   
    car_fuel ENUM('DIESEL', 'GASOLINE', 'ELECTRIC') NOT NULL,                   
    car_reg_year SMALLINT NOT NULL,               
    car_reg_month SMALLINT NOT NULL,              
    car_odometer INT NOT NULL,                    
    car_type ENUM('LUXURY', 'FAMILY', 'SPORT') NOT NULL,  
    car_status ENUM('AVAILABLE', 'RENTED') NOT NULL DEFAULT 'AVAILABLE', 
    engine_size INT,                              
    horsepower INT,                               
    seats INT,                                    
    automatic_gear BOOLEAN,                       
    air_condition BOOLEAN,                        
    cruise_control BOOLEAN,                       
    leather_seats BOOLEAN,                       
    PRIMARY KEY (car_id)                          
);

	CREATE TABLE IF NOT EXISTS city
	(
	city_name VARCHAR(30) NOT NULL,	
	zip_code VARCHAR(20) NOT NULL,
	PRIMARY KEY (zip_code)
	);

	CREATE TABLE IF NOT EXISTS customer
	(
	customer_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	phone_no VARCHAR(20),
	email VARCHAR (60),
	license_no VARCHAR(50) NOT NULL UNIQUE,
	address VARCHAR(50),
	zip_code VARCHAR(20) NOT NULL,
	driver_since DATE NOT NULL, 
	FOREIGN KEY (zip_code) REFERENCES city(zip_code),
	PRIMARY KEY (customer_id)
	);

	CREATE TABLE IF NOT EXISTS rental
	(
	rental_id INT NOT NULL AUTO_INCREMENT,
	customer_id INT NOT NULL,
	car_id INT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE,
    max_km INT,
    km_driven INT,
	rental_status ENUM('ACTIVE','COMPLETED') DEFAULT 'ACTIVE',
	FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
	FOREIGN KEY(car_id) REFERENCES car(car_id),
	PRIMARY KEY (rental_id)
	);

INSERT INTO city (city_name, zip_code) VALUES 
('Copenhagen', '1000'),
('Aarhus', '8000'),
('Odense', '5000'),
('Aalborg', '9000'),
('Esbjerg', '6700');

	
INSERT INTO customer (name, phone_no, email, license_no, address, zip_code, driver_since) VALUES
('Lars Jensen', '12345678', 'lars.jensen@example.com', 'AB123456', 'Main Street 12', '1000', '2010-05-10'),
('Mette Hansen', '23456789', 'mette.hansen@example.com', 'BC234567', 'Baker Street 22', '8000', '2012-07-21'),
('Anders Nielsen', '34567890', 'anders.nielsen@example.com', 'CD345678', 'King Street 15', '5000', '2015-02-15'),
('Sofie Christensen', '45678901', 'sofie.christensen@example.com', 'DE456789', 'Ocean View 5', '9000', '2018-03-30'),
('Peter Sørensen', '56789012', 'peter.sorensen@example.com', 'EF567890', 'Park Lane 18', '6700', '2020-11-05');


INSERT INTO car (car_brand, car_model, car_plate, car_fuel, car_reg_year, car_reg_month, car_odometer, car_type, car_status, engine_size, horsepower, seats, automatic_gear, air_condition, cruise_control, leather_seats)
VALUES
('Volvo', 'XC90', 'ABC123', 'GASOLINE', 2018, 5, 50000, 'LUXURY', 'AVAILABLE', 2000, 300, 5, TRUE, TRUE, TRUE, TRUE),
('BMW', 'X5', 'XYZ789', 'DIESEL', 2017, 8, 60000, 'LUXURY', 'AVAILABLE', 3000, 350, 5, TRUE, TRUE, TRUE, TRUE),
('Toyota', 'Camry', 'DEF456', 'GASOLINE', 2016, 10, 70000, 'FAMILY', 'AVAILABLE', 1800, 250, 5, TRUE, TRUE, TRUE, FALSE),
('Honda', 'Civic', 'JKL321', 'GASOLINE', 2020, 3, 15000, 'SPORT', 'AVAILABLE', 1500, 220, 4, TRUE, TRUE, FALSE, FALSE),
('Audi', 'A4', 'GHI654', 'ELECTRIC', 2021, 6, 10000, 'SPORT', 'AVAILABLE', 0, 200, 4, TRUE, TRUE, TRUE, FALSE);


INSERT INTO rental (customer_id, car_id, start_date, end_date, max_km, km_driven, rental_status) VALUES
(1, 1, '2025-02-01', '2025-02-10', 500, 480, 'COMPLETED'),
(2, 2, '2025-01-15', '2025-01-20', 400, 350, 'COMPLETED'),
(3, 3, '2025-02-05', '2025-02-12', 600, 500, 'ACTIVE'),
(4, 4, '2025-02-10', '2025-02-18', 450, 400, 'ACTIVE'),
(5, 5, '2025-02-12', '2025-02-20', 550, 520, 'ACTIVE');

