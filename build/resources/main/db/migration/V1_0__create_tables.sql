CREATE TABLE brand (
  name VARCHAR(8) PRIMARY KEY,
  rate_formula VARCHAR(255),
  UNIQUE(name)
);

CREATE TABLE person (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(127),
  surname VARCHAR(127),
  dni VARCHAR(15) NOT NULL,
  birthdate DATE,
  email VARCHAR(255),
  UNIQUE(dni)
);

CREATE TABLE card (
  id VARCHAR(36) PRIMARY KEY,
  brand_name VARCHAR(8) NOT NULL,
  number VARCHAR(16) NOT NULL,
  cvv VARCHAR(3) NOT NULL,
  expiration DATE NOT NULL,
  fullname VARCHAR(255) NOT NULL,
  person_id VARCHAR(36) NOT NULL,
  UNIQUE(number),
  FOREIGN KEY (brand_name) REFERENCES brand(name),
  FOREIGN KEY (person_id) REFERENCES person(id)
);


