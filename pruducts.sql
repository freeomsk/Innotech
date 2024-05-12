CREATE TABLE products
(
    id             SERIAL PRIMARY KEY,
    user_id        BIGINT         NOT NULL,
    account_number VARCHAR(255)   NOT NULL,
    balance        NUMERIC(19, 2) NOT NULL,
    product_type   VARCHAR(50)    NOT NULL
);