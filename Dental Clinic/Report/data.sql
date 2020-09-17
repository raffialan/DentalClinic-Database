INSERT INTO
  	patient (name, sex, age, address, email, contactNo)
    VALUES
    	('Marc', 'Male', 30, 'Montreal', 'marc@gmail.com', 5144567890),
    	('Marry', 'Female', 20, 'Laval', 'marry@gmail.com', 5145687235),
    	('John', 'Male', 36, 'Verdun', 'john@gmail.com', 5147612349),
    	('Richard', 'Male', 65, 'Montreal', 'richard@gmail.com', 5149834764),
    	('Lisa', 'Female', 54, 'Toronto', 'lisa@gmail.com', 5141265437),
    	('Ali', 'Male', 22, 'Quebec City', 'ali@gmail.com', 5148745098);


INSERT INTO
    clinic (name, address)
    VALUES
      	('AMS Clinic', 'Montreal'),
      	('FNP Clinic', 'Laval'),
      	('HLM Clinic', 'Verdun'),
      	('SWE Clinic', 'Toronto'),
      	('HHP Clinic', 'Montreal'),
      	('SSP Clinic', 'Quebec City');

INSERT INTO
    	staff (name, role, cid, sex, age, email, contactNo)
      VALUES
      	('William', 'Dentist', 1, 'Male', 34, 'William@gmail.com', 5142234567),
      	('james', 'Dentist', 2, 'Male', 37, 'james@gmail.com', 5141234567),
      	('harper', 'Dentist', 3, 'Male', 50, 'harper@gmail.com', 5147654324),
      	('Ella', 'Dentist', 4, 'Female', 45, 'Ella@gmail.com', 51448767560),
      	('Avery', 'Dentist', 5, 'Male', 45, 'Avery@gmail.com', 5140956096),
      	('Jackson', 'Dentist', 6, 'Male', 50, 'Jackson@gmail.com', 5148789087),
      	('Madison', 'Receptionists', 1, 'Male', 54, 'Madison@gmail.com', 5142343675),
      	('Lily', 'Receptionists', 1, 'Female', 45, 'Lily@gmail.com', 5148789675),
      	('Julian', 'Receptionists', 1, 'Female', 54, 'Julian@gmail.com', 5142134542),
      	('Addison', 'Dentist', 1, 'Male', 60, 'Addison@gmail.com', 5149087908),
      	('Grayson', 'Dentist', 5, 'Male', 34, 'Grayson@gmail.com', 5141232453),
      	('Jack', 'Assistants', 1, 'Male', 44, 'Jack@gmail.com', 5145643568),
      	('Carter', 'Assistants', 1, 'Male', 43, 'Carter@gmail.com', 5149858953);

INSERT INTO
      appointment (description,appointmentDate,pid,sid,cid,isVisit)
      VALUES
        ('Regular Check-up',20200401,1,1,1,'Yes'),
        ('Regular Check-up',20200402,2,1,1,'Yes'),
        ('Regular Check-up',20200403,3,1,1,'Yes'),
        ('Regular Check-up',20200404,4,1,1,'Yes'),

        ('Regular Check-up',20200404,6,5,5,'Yes'),
        ('Regular Check-up',20200404,3,11,5,'Yes'),
        ('Regular Check-up',20200404,2,11,5,'Yes'),
        ('Regular Check-up',20200404,1,5,5,'Yes'),

        ('Regular Check-up',20200405,1,4,4,'No'),
        ('Regular Check-up',20200406,1,4,4,'No'),
        ('Regular Check-up',20200407,2,2,2,'No'),
        ('Regular Check-up',20200408,2,2,2,'No'),
        ('Regular Check-up',20200409,3,3,3,'No'),
        ('Regular Check-up',20200410,3,3,3,'No'),
        ('Regular Check-up',20200411,5,5,5,'No'),
        ('Regular Check-up',20200412,5,5,5,'No');

INSERT INTO
      treatment (name,aid,cost)
      VALUES
        ('Bonding',1,100),
        ('Braces',1,100),
        ('Gum Surgery',2,100.50),
        ('Bonding',2,100),
        ('Braces',3,100),
        ('Gum Surgery',3,100.15),
        ('Bonding',4,100),
        ('Braces',4,100),
        ('Gum Surgery',5,200),
        ('Bonding',6,100),
        ('Braces',11,100),
        ('Gum Surgery',7,300),
        ('Bonding',8,100),
        ('Braces',9,100),
        ('Gum Surgery',10,400),
        ('Bonding',12,100),
        ('Braces',13,100),
        ('Gum Surgery',14,500),
        ('Bonding',15,100),
        ('Braces',16,100),
        ('Gum Surgery',16,600),
        ('Bonding',15,100),
        ('Braces',14,100),
        ('Gum Surgery',13,700);


  INSERT INTO
      bill (aid,totalAmount,isPaid)
      VALUES
        (1,1400,'Yes'),
        (2,1350.50,'Yes'),
        (3,566,'Yes'),
        (4,333,'No'),
        (5,654,'Yes'),
        (6,332,'No'),
        (7,553,'Yes'),
        (8,785,'Yes'),
        (9,325,'No'),
        (10,789,'Yes'),
        (11,213,'Yes'),
        (12,563,'No'),
        (13,789,'Yes'),
        (14,236,'No'),
        (15,900,'Yes'),
        (16,1000,'No');