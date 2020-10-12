DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS address;
CREATE TABLE employee
(
   id INT PRIMARY KEY,
   first_name VARCHAR (250) NOT NULL,
   last_name VARCHAR (250) NOT NULL,
   date_of_birth DATE (250) DEFAULT NULL
);
CREATE TABLE address
(
     address_id  INT AUTO_INCREMENT  PRIMARY KEY,
    line1 VARCHAR (250) NOT NULL,
   line2 VARCHAR (250),
   city VARCHAR (250) NOT NULL,
   state VARCHAR (250) NOT NULL,
   country VARCHAR (250) NOT NULL,
   zipcode INT NOT NULL,
   id BIGINT,
   foreign key (id) references employee (id)
);