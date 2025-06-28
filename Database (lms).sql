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
    principal_amount DECIMAL,
    interest_rate DECIMAL,
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
(101, 'Pravalika', 'pravalika@example.com', '1111111111', 'Chennai', 650),
(102, 'Rashmika', 'rashmika@example.com', '2222222222', 'Hyderabad', 500),
(103, 'Rahul', 'rahul@example.com', '3333333333', 'Chennai', 700),
(104, 'Aarav', 'aarav@example.com', '4444444444', 'Hyderabad', 750),
(105, 'Sanya', 'sanya@example.com', '5555555555', 'Bangalore', 820),
(106, 'Rohit', 'rohit@example.com', '6666666666', 'Hyderabad', 700),
(107, 'Priya', 'verma@example.com', '7777777777', 'Pune', 780),
(108, 'Manik', 'manik@example.com', '8888888888', 'Mumbai', 300),
(109, 'Nandhini', 'nandhini@example.com', '9999999999', 'Banglore', 812),
(110, 'Cabir', 'cabir@example.com', '1010101010', 'Mumbai', 600);

INSERT INTO loan (
    loan_id, customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status
) VALUES
(1, 101, 400000, 8.5, 60, 'HomeLoan', 'Approved'),
(2, 102, 250000, 9.2, 48, 'CarLoan', 'Rejected'),
(3, 103, 300000, 8.0, 36, 'CarLoan', 'Approved'),
(4, 104, 500000, 7.8, 72, 'HomeLoan', 'Approved'),
(5, 105, 600000, 7.5, 84, 'HomeLoan', 'Approved'),
(6, 106, 350000, 9.0, 48, 'CarLoan', 'Rejected'),
(7, 107, 450000, 8.2, 60, 'HomeLoan', 'Approved'),
(8, 108, 200000, 10.5, 36, 'CarLoan', 'Rejected'),
(9, 109, 380000, 8.3, 48, 'HomeLoan', 'Approved'),
(10, 110, 220000, 9.8, 36, 'CarLoan', 'Rejected');

INSERT INTO home_loan (
    loan_id, property_address, property_value
) VALUES
(1, 'Velachery, Chennai', 600000),
(4, 'Jubilee Hills, Hyderabad', 750000),
(5, 'Whitefield, Bangalore', 1000000),
(7, 'Koregaon Park, Pune', 680000),
(9, 'BTM Layout, Bangalore', 720000);

INSERT INTO car_loan (
    loan_id, car_model, car_value
) VALUES
(2, 'Hyundai i20', 300000),
(3, 'Honda City', 400000),
(6, 'Maruti Brezza', 350000),
(8, 'Renault Kwid', 250000),
(10, 'Tata Tiago', 270000);

select * from loan;
select * from customer;
select * from car_loan;
select * from home_loan;


