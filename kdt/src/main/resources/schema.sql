CREATE table customers
(
    customer_id   BINARY(16) PRIMARY KEY,
    name          varchar(20) Not null,
    email         varchar(50) Not null,
    last_login_at datetime(6)          default null,
    created_at    datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    constraint unq_user_email unique (email)
);
