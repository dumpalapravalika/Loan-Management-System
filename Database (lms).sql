create database loan_db;

use loan_db;

CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    address VARCHAR(255),
    credit_score INT
);

CREATE TABLE loan (
    loan_id INT PRIMARY KEY,
    customer_id INT,
    principal_amount DECIMAL(10,2),
    interest_rate DECIMAL(5,2),
    loan_term INT,
    loan_type VARCHAR(20),
    status VARCHAR(20),
    interest_amount DECIMAL(10,2),
    emi_amount DECIMAL(10,2),
    remaining_emi INT,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE car_loan (
    loan_id INT PRIMARY KEY,
    car_model VARCHAR(100),
    car_value DECIMAL(10,2),
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE home_loan (
    loan_id INT PRIMARY KEY,
    property_address VARCHAR(255),
    property_value DECIMAL(10,2),
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

drop table customer,loan,car_loan,home_loan;

CREATE TABLE customer (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15),
    address VARCHAR(255),
    credit_score INT
);

CREATE TABLE loan (
    loan_id INT PRIMARY KEY,
    customer_id INT,
    principal_amount DECIMAL(10,2),
    interest_rate DECIMAL(5,2),
    loan_term INT,
    loan_type VARCHAR(20),
    loan_status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE home_loan (
    loan_id INT PRIMARY KEY,
    property_address VARCHAR(255),
    property_value INT,
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE car_loan (
    loan_id INT PRIMARY KEY,
    car_model VARCHAR(100),
    car_value INT,
    FOREIGN KEY (loan_id) REFERENCES loan(loan_id)
);

INSERT INTO customer (
    customer_id, name, email, phone, address, credit_score
) VALUES
(101, 'Pravalika', 'pravalika@example.com', '1234567890', 'Chennai', 650),
(102, 'Rashmika', 'rashmika@example.com', '9876543210', 'Hyderabad', 500),
(103, 'Rahul', 'rahul@example.com', '78992134561', 'Chennai', 700),
(104, 'Aarav', 'aarav@example.com', '9876543210', 'Hyderabad', 750),
(105, 'Sanya', 'sanya@example.com', '9898989898', 'Bangalore', 820),
(106, 'Rohit', 'rohit@example.com', '9123456789', 'Hyderabad', 700),
(107, 'Priya', 'verma@example.com', '9345678912', 'Pune', 780);

INSERT INTO loan (
    loan_id, customer_id, principal_amount, interest_rate, loan_term,
    loan_type, status, property_address, property_value,
    car_model, car_value
) VALUES
(4, 104, 500000, 8.5, 60, 'HomeLoan', 'Pending', 'Madhapur, Hyderabad', 650000, NULL, NULL),
(5, 105, 800000, 9.0, 120, 'HomeLoan', 'Approved', 'Whitefield, Bangalore', 1000000, NULL, NULL);

INSERT INTO loan (
    loan_id, customer_id, principal_amount, interest_rate, term,
    loan_type, status, property_address, property_value,
    car_model, car_value
) VALUES
(3, 103, 300000, 9.2, 48, 'CarLoan', 'Pending', NULL, NULL, 'Honda Amaze', 400000),
(4, 104, 450000, 8.8, 60, 'CarLoan', 'Approved', NULL, NULL, 'Hyundai Verna', 550000);

select * from loan;
select * from customer;
select * from car_loan;
select * from home_loan;


