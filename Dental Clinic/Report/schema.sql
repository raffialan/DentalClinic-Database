DROP TABLE IF EXISTS patient;
DROP TABLE IF EXISTS clinic;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS appointment;
DROP TABLE IF EXISTS treatment;
DROP TABLE IF EXISTS bill;

CREATE TABLE patient (
  pid INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255) NOT NULL ,
  sex VARCHAR(255) NOT NULL ,
  age INT NOT NULL ,
  address VARCHAR(255) NOT NULL ,
  email VARCHAR(255) NOT NULL ,
  contactNo BIGINT NOT NULL
  );

CREATE TABLE clinic (
  cid INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255) NOT NULL ,
  address VARCHAR(255) NOT NULL
  );

CREATE TABLE staff (
  sid INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255) NOT NULL ,
  role VARCHAR(255) NOT NULL ,
  cid INT NOT NULL ,
    CONSTRAINT fk_clinicId_staff FOREIGN KEY (cid) REFERENCES clinic(cid),
  sex VARCHAR(255) NOT NULL ,
  age INT NOT NULL ,
  email VARCHAR(255) NOT NULL ,
  contactNo BIGINT NOT NULL
  );

CREATE TABLE appointment (
  aid INT AUTO_INCREMENT PRIMARY KEY ,
  description VARCHAR(255) NOT NULL ,
  appointmentDate DATE NOT NULL,
  pid INT NOT NULL ,
    CONSTRAINT fk_patientId_appointment FOREIGN KEY (pid) REFERENCES patient(pid),
  sid INT NULL ,
    CONSTRAINT fk_staffId_appointment FOREIGN KEY (sid) REFERENCES staff(sid),
  cid INT NOT NULL ,
    CONSTRAINT fk_clinicId_appointment FOREIGN KEY (cid) REFERENCES clinic(cid),
  isVisit VARCHAR(255) NULL
  );


CREATE TABLE treatment (
  tid INT AUTO_INCREMENT PRIMARY KEY ,
  name VARCHAR(255) NOT NULL ,
  aid INT NOT NULL ,
    CONSTRAINT fk_aid_treatment FOREIGN KEY (aid) REFERENCES appointment(aid),
  cost DOUBLE NOT NULL
  );


CREATE TABLE bill (
  bid INT AUTO_INCREMENT PRIMARY KEY ,
  aid INT NOT NULL ,
    CONSTRAINT fk_aid_bill FOREIGN KEY (aid) REFERENCES appointment(aid),
  totalAmount DOUBLE NOT NULL ,
  isPaid VARCHAR(255) NOT NULL
  );
