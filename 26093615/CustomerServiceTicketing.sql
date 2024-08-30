CREATE DATABASE customerService;

USE customerService;


CREATE TABLE Ticket(
ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    creation_date DATETIME,
    issue_description TEXT,
    status VARCHAR(50)
);


CREATE TABLE Assignment (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT,
    representative_id INT,
    assignment_date DATETIME,
    status VARCHAR(50),
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
);

CREATE TABLE Resolution (
    resolution_id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT,
    resolution_date DATETIME,
    resolution_details TEXT,
    status VARCHAR(50),
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id)
);


