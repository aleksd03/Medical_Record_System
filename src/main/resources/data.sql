INSERT IGNORE INTO roles (authority) VALUES ('ADMIN');
INSERT IGNORE INTO roles (authority) VALUES ('DOCTOR');
INSERT IGNORE INTO roles (authority) VALUES ('PATIENT');

INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('admin', '$2a$10$CTJxaL1Jpq1VS1wlrAqXhOPyAKKPtoII2RmI/QiwYZmQlgCHJLfVm', true, true, true, true);

INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('doctor', '$2a$10$dsmEAEHYGZcS3RfOd.4qwOIw2G5NynR3kNWWpBGNheb5h31LG69NC', true, true, true, true);

INSERT IGNORE INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('patient', '$2a$10$dnozTEToMmWM3R4IO.qkcO8JrhwFcUgMI42FAcLkOpL.OUWxaOqPC', true, true, true, true);

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.authority = 'ADMIN';

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'doctor' AND r.authority = 'DOCTOR';

INSERT IGNORE INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'patient' AND r.authority = 'PATIENT';

# admin123: $2a$10$CTJxaL1Jpq1VS1wlrAqXhOPyAKKPtoII2RmI/QiwYZmQlgCHJLfVm
# doctor123: $2a$10$dsmEAEHYGZcS3RfOd.4qwOIw2G5NynR3kNWWpBGNheb5h31LG69NC
# patient123: $2a$10$dnozTEToMmWM3R4IO.qkcO8JrhwFcUgMI42FAcLkOpL.OUWxaOqPC