INSERT INTO patient (name, gender, birth_date,email,blood_group)
VALUES
('Aarav Sharma','MALE','03-06-1999','aaravsharma@gmail.com','B_POSITIVE'),
('Priya Sharma','FEMALE','13-07-1998','priyasharma@gmail.com','AB_NEGATIVE'),
('Mohan Sharma','MALE','23-08-1997','mohansharma@gmail.com','O_NEGATIVE'),
('Akash Sharma','MALE','20-09-1996','akashsharma@gmail.com','A_NEGATIVE'),
('Vipul Sharma','MALE','22-10-1995','vipulsharma@gmail.com','A_POSITIVE');

INSERT INTO doctor(name,specialization,email)
VALUES
('Dr Rakesh Mehta','Cardiologist','rakesh.mehta@example.com'),
('Dr Harshit Bansal','Gynologist','harshit.bansal@example.com'),
('Dr Arjun Sharma','Orthopedics','arjun.mehta@example.com');

INSERT INTO appointment(appointment_time,reason,doctor_id,patient_id)
VALUES
('2025-07-01 10:30:00','General Checkup',1,2),
('2025-07-02 11:00:00','Skin Rash',2,2),
('2025-07-03 10:45:00','Knee Pain',3,3),
('2025-07-04 14:00:00','follow-up visit',1,1),
('2025-07-05 16:15:00','Consultation',1,4),
('2025-07-06 08:30:00','Allergy-Treatment',2,5);