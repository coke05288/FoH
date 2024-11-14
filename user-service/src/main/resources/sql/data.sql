INSERT INTO `users` (`name`, `email`, `age`, `password`, `created_at`, `updated_at`)
VALUES
    ('김영준', 'coke05288@gmail.com', 29, '1q2w3e4r', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('김미진', 'avb12340@gmail.com', 25,'1q2w3e4r', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('천시아', '1000yums@gmail.com', 25,'1q2w3e4r', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    ('한준영', 'expershyp77@gmail.com', 29,'1q2w3e4r', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

INSERT INTO `user_login_history` (`user_id`, `login_time`, `ip_address`)
VALUES
    (1, CURRENT_TIMESTAMP(), '192.168.1.1'),
    (2, CURRENT_TIMESTAMP(), '192.168.1.2'),
    (3, CURRENT_TIMESTAMP(), '192.168.1.3'),
    (4, CURRENT_TIMESTAMP(), '192.168.1.4');