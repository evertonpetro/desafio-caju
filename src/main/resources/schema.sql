CREATE TABLE balances
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR2,
    amount   DECIMAL(10, 2)
);

CREATE TABLE transactions
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    account     VARCHAR2,
    totalAmount DECIMAL(10, 2),
    merchant    VARCHAR2,
    mcc         VARCHAR2
);

CREATE TABLE mcc_category
(
    start_mcc INTEGER,
    end_mcc   INTEGER,
    category  VARCHAR2
);

CREATE TABLE merchants
(
    id            SERIAL PRIMARY KEY,
    merchant_name VARCHAR(255),
    mcc           VARCHAR(4)
);