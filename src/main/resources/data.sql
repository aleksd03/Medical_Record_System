INSERT IGNORE INTO roles (authority) VALUES ('ADMIN');
INSERT IGNORE INTO roles (authority) VALUES ('DOCTOR');
INSERT IGNORE INTO roles (authority) VALUES ('PATIENT');

-- DOCTORS
INSERT IGNORE INTO doctors (first_name, last_name, identification_number, specialty, gp) VALUES
('Ivan', 'Kolev', 'IK001', 'General Practice', true),
('Maria', 'Petrova', 'MP001', 'Cardiology', false),
('Georgi', 'Dimitrov', 'GD001', 'Neurology', false),
('Elena', 'Stoyanova', 'ES001', 'Pediatrics', false),
('Nikolay', 'Ivanov', 'NI001', 'Orthopedics', false),
('Anna', 'Georgieva', 'AG001', 'General Practice', true),
('Petar', 'Todorov', 'PT001', 'Dermatology', false),
('Sofia', 'Marinova', 'SM001', 'Ophthalmology', false);

-- PATIENTS
INSERT IGNORE INTO patients (first_name, last_name, egn, health_insured, general_practitioner_id) VALUES
('Peter', 'Nikolov', '8503191234', false, (SELECT id FROM doctors WHERE identification_number = 'IK001')),
('Teodora', 'Hristova', '9001011234', true, (SELECT id FROM doctors WHERE identification_number = 'IK001')),
('Dimitar', 'Vasilev', '7506151234', true, (SELECT id FROM doctors WHERE identification_number = 'AG001')),
('Silviya', 'Angelova', '8807221234', false, (SELECT id FROM doctors WHERE identification_number = 'AG001')),
('Martin', 'Kostadinov', '9204181234', true, (SELECT id FROM doctors WHERE identification_number = 'IK001')),
('Ralitsa', 'Todorova', '8612091234', true, (SELECT id FROM doctors WHERE identification_number = 'AG001')),
('Hristo', 'Petrov', '7901241234', false, (SELECT id FROM doctors WHERE identification_number = 'IK001')),
('Gabriela', 'Ivanova', '9505301234', true, (SELECT id FROM doctors WHERE identification_number = 'AG001')),
('Stefan', 'Georgiev', '8308171234', true, (SELECT id FROM doctors WHERE identification_number = 'IK001')),
('Kremena', 'Dimitrova', '9102141234', false, (SELECT id FROM doctors WHERE identification_number = 'AG001'));

-- DIAGNOSES
INSERT IGNORE INTO diagnoses (name, description) VALUES
('Influenza', 'Viral infection affecting the respiratory system'),
('Hypertension', 'High blood pressure condition'),
('Diabetes Type 2', 'Chronic condition affecting blood sugar regulation'),
('Bronchitis', 'Inflammation of the bronchial tubes'),
('Migraine', 'Severe recurring headaches'),
('Osteoarthritis', 'Degenerative joint disease'),
('Conjunctivitis', 'Inflammation of the eye conjunctiva'),
('Dermatitis', 'Skin inflammation condition'),
('Anemia', 'Low red blood cell count'),
('Anxiety Disorder', 'Chronic anxiety and stress condition');

-- APPOINTMENTS
INSERT IGNORE INTO appointments (date, doctor_id, patient_id, diagnosis_id, treatment, price) VALUES
('2026-01-05', (SELECT id FROM doctors WHERE identification_number = 'IK001'), (SELECT id FROM patients WHERE egn = '8503191234'), (SELECT id FROM diagnoses WHERE name = 'Influenza'), 'Rest and fluids', 50.00),
('2026-01-10', (SELECT id FROM doctors WHERE identification_number = 'MP001'), (SELECT id FROM patients WHERE egn = '9001011234'), (SELECT id FROM diagnoses WHERE name = 'Hypertension'), 'Medication prescribed', 80.00),
('2026-01-15', (SELECT id FROM doctors WHERE identification_number = 'GD001'), (SELECT id FROM patients WHERE egn = '7506151234'), (SELECT id FROM diagnoses WHERE name = 'Migraine'), 'Pain relief medication', 90.00),
('2026-01-20', (SELECT id FROM doctors WHERE identification_number = 'IK001'), (SELECT id FROM patients WHERE egn = '8807221234'), (SELECT id FROM diagnoses WHERE name = 'Bronchitis'), 'Antibiotics prescribed', 60.00),
('2026-01-25', (SELECT id FROM doctors WHERE identification_number = 'ES001'), (SELECT id FROM patients WHERE egn = '9204181234'), (SELECT id FROM diagnoses WHERE name = 'Influenza'), 'Rest and medication', 50.00),
('2026-02-01', (SELECT id FROM doctors WHERE identification_number = 'NI001'), (SELECT id FROM patients WHERE egn = '8612091234'), (SELECT id FROM diagnoses WHERE name = 'Osteoarthritis'), 'Physiotherapy recommended', 120.00),
('2026-02-05', (SELECT id FROM doctors WHERE identification_number = 'PT001'), (SELECT id FROM patients WHERE egn = '7901241234'), (SELECT id FROM diagnoses WHERE name = 'Dermatitis'), 'Topical cream prescribed', 70.00),
('2026-02-10', (SELECT id FROM doctors WHERE identification_number = 'SM001'), (SELECT id FROM patients WHERE egn = '9505301234'), (SELECT id FROM diagnoses WHERE name = 'Conjunctivitis'), 'Eye drops prescribed', 65.00),
('2026-02-15', (SELECT id FROM doctors WHERE identification_number = 'MP001'), (SELECT id FROM patients WHERE egn = '8308171234'), (SELECT id FROM diagnoses WHERE name = 'Hypertension'), 'Medication adjusted', 80.00),
('2026-02-20', (SELECT id FROM doctors WHERE identification_number = 'IK001'), (SELECT id FROM patients WHERE egn = '9102141234'), (SELECT id FROM diagnoses WHERE name = 'Anemia'), 'Iron supplements prescribed', 55.00),
('2026-02-25', (SELECT id FROM doctors WHERE identification_number = 'GD001'), (SELECT id FROM patients WHERE egn = '8503191234'), (SELECT id FROM diagnoses WHERE name = 'Anxiety Disorder'), 'Therapy recommended', 100.00),
('2026-03-01', (SELECT id FROM doctors WHERE identification_number = 'AG001'), (SELECT id FROM patients WHERE egn = '9001011234'), (SELECT id FROM diagnoses WHERE name = 'Diabetes Type 2'), 'Diet and medication', 90.00),
('2026-03-05', (SELECT id FROM doctors WHERE identification_number = 'IK001'), (SELECT id FROM patients WHERE egn = '7506151234'), (SELECT id FROM diagnoses WHERE name = 'Influenza'), 'Rest and fluids', 50.00),
('2026-03-10', (SELECT id FROM doctors WHERE identification_number = 'MP001'), (SELECT id FROM patients WHERE egn = '8807221234'), (SELECT id FROM diagnoses WHERE name = 'Hypertension'), 'Medication prescribed', 80.00),
('2026-03-15', (SELECT id FROM doctors WHERE identification_number = 'NI001'), (SELECT id FROM patients WHERE egn = '9204181234'), (SELECT id FROM diagnoses WHERE name = 'Osteoarthritis'), 'Physiotherapy continued', 120.00),
('2026-03-20', (SELECT id FROM doctors WHERE identification_number = 'ES001'), (SELECT id FROM patients WHERE egn = '8612091234'), (SELECT id FROM diagnoses WHERE name = 'Bronchitis'), 'Antibiotics prescribed', 60.00),
('2026-03-25', (SELECT id FROM doctors WHERE identification_number = 'PT001'), (SELECT id FROM patients WHERE egn = '7901241234'), (SELECT id FROM diagnoses WHERE name = 'Dermatitis'), 'Treatment continued', 70.00),
('2026-04-01', (SELECT id FROM doctors WHERE identification_number = 'SM001'), (SELECT id FROM patients WHERE egn = '9505301234'), (SELECT id FROM diagnoses WHERE name = 'Conjunctivitis'), 'Follow up check', 65.00),
('2026-04-05', (SELECT id FROM doctors WHERE identification_number = 'GD001'), (SELECT id FROM patients WHERE egn = '8308171234'), (SELECT id FROM diagnoses WHERE name = 'Migraine'), 'New medication prescribed', 90.00),
('2026-04-10', (SELECT id FROM doctors WHERE identification_number = 'AG001'), (SELECT id FROM patients WHERE egn = '9102141234'), (SELECT id FROM diagnoses WHERE name = 'Diabetes Type 2'), 'Insulin adjusted', 90.00);

-- SICK LEAVES
INSERT IGNORE INTO sick_leaves (start_date, number_of_days, appointment_id) VALUES
('2026-01-05', 7, (SELECT id FROM appointments WHERE date = '2026-01-05' AND patient_id = (SELECT id FROM patients WHERE egn = '8503191234'))),
('2026-01-20', 5, (SELECT id FROM appointments WHERE date = '2026-01-20' AND patient_id = (SELECT id FROM patients WHERE egn = '8807221234'))),
('2026-01-25', 3, (SELECT id FROM appointments WHERE date = '2026-01-25' AND patient_id = (SELECT id FROM patients WHERE egn = '9204181234'))),
('2026-02-01', 14, (SELECT id FROM appointments WHERE date = '2026-02-01' AND patient_id = (SELECT id FROM patients WHERE egn = '8612091234'))),
('2026-02-05', 4, (SELECT id FROM appointments WHERE date = '2026-02-05' AND patient_id = (SELECT id FROM patients WHERE egn = '7901241234'))),
('2026-03-05', 6, (SELECT id FROM appointments WHERE date = '2026-03-05' AND patient_id = (SELECT id FROM patients WHERE egn = '7506151234'))),
('2026-03-20', 5, (SELECT id FROM appointments WHERE date = '2026-03-20' AND patient_id = (SELECT id FROM patients WHERE egn = '8612091234'))),
('2026-04-05', 3, (SELECT id FROM appointments WHERE date = '2026-04-05' AND patient_id = (SELECT id FROM patients WHERE egn = '8308171234')));

-- USERS
INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('admin', '$2a$10$CTJxaL1Jpq1VS1wlrAqXhOPyAKKPtoII2RmI/QiwYZmQlgCHJLfVm', true, true, true, true);

INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('doctor', '$2a$10$dsmEAEHYGZcS3RfOd.4qwOIw2G5NynR3kNWWpBGNheb5h31LG69NC', true, true, true, true);

INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('patient', '$2a$10$dnozTEToMmWM3R4IO.qkcO8JrhwFcUgMI42FAcLkOpL.OUWxaOqPC', true, true, true, true);

-- USERS ROLES
INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.authority = 'ADMIN';

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'doctor' AND r.authority = 'DOCTOR';

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'patient' AND r.authority = 'PATIENT';

-- LINK USERS TO DOCTOR/PATIENT
UPDATE users SET doctor_id = (SELECT id FROM doctors WHERE identification_number = 'IK001') WHERE username = 'doctor';
UPDATE users SET patient_id = (SELECT id FROM patients WHERE egn = '8503191234') WHERE username = 'patient';

-- PASSWORDS
# admin123: $2a$10$CTJxaL1Jpq1VS1wlrAqXhOPyAKKPtoII2RmI/QiwYZmQlgCHJLfVm
# doctor123: $2a$10$dsmEAEHYGZcS3RfOd.4qwOIw2G5NynR3kNWWpBGNheb5h31LG69NC
# patient123: $2a$10$dnozTEToMmWM3R4IO.qkcO8JrhwFcUgMI42FAcLkOpL.OUWxaOqPC