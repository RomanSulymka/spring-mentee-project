CREATE TABLE user_accounts
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL
);

CREATE TABLE money
(
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    symbol VARCHAR(255) NOT NULL,
    UNIQUE (name, symbol)
);

CREATE TABLE balances
(
    id              BIGSERIAL PRIMARY KEY,
    money_id        BIGINT           NOT NULL,
    balance         DOUBLE PRECISION NOT NULL,
    user_account_id BIGINT           NOT NULL,
    FOREIGN KEY (user_account_id) REFERENCES user_accounts (id),
    FOREIGN KEY (money_id) REFERENCES money (id)
);

CREATE TABLE wallets
(
    id          BIGSERIAL PRIMARY KEY,
    wallet_name VARCHAR(255) NOT NULL,
    balance_id  BIGINT       NOT NULL,
    UNIQUE (balance_id),
    FOREIGN KEY (balance_id) REFERENCES balances (id)
);

CREATE TABLE transfer_requests
(
    id                  BIGSERIAL PRIMARY KEY ,
    sender_account_id   bigint NOT NULL,
    receiver_account_id bigint NOT NULL,
    sender_wallet_id    bigint NOT NULL,
    receiver_wallet_id  bigint NOT NULL,
    amount              double PRECISION NOT NULL,
    FOREIGN KEY (sender_account_id) REFERENCES user_accounts (id),
    FOREIGN KEY (receiver_account_id) REFERENCES user_accounts (id)
);

CREATE TABLE transfers
(
    id          BIGSERIAL PRIMARY KEY,
    status              varchar(255) NOT NULL,
    transaction_date    timestamp    NOT NULL,
    transfer_request_id bigint,
    sender_wallet_id    bigint NOT NULL,
    receiver_wallet_id  bigint NOT NULL,
    FOREIGN KEY (transfer_request_id) REFERENCES transfer_requests (id)
);


INSERT INTO money (name, symbol)
VALUES ('US Dollar', 'USD'),
       ('Euro', 'EUR'),
       ('British Pound', 'GBP'),
       ('Japanese Yen', 'JPY'),
       ('Canadian Dollar', 'CAD'),
       ('Australian Dollar', 'AUD'),
       ('Swiss Franc', 'CHF'),
       ('New Zealand Dollar', 'NZD');

INSERT INTO user_accounts (first_name, last_name, email, password)
VALUES ('John', 'Doe', 'johndoe@example.com', 'password123'),
       ('William', 'Brown', 'williambrown@example.com', 'password789'),
       ('Bob', 'Johnson', 'bobjohnson@example.com', 'password789'),
       ('roman', 'sulymka', 'roman@mail.com', '$2a$10$ek9m13baSIMPPtAaqNEaie3MWmP5UNuvsO/b2GuuN4jVH66zAF2bu');

INSERT INTO balances (balance, user_account_id, money_id)
VALUES (1000.00, 1, 1),
       (5000.00, 2, 2),
       (250.00, 3, 3);

INSERT INTO wallets (wallet_name, balance_id)
VALUES ('Main Wallet', 1),
       ('Savings', 2),
       ('Travel', 3);

INSERT INTO transfer_requests (sender_account_id, receiver_account_id, amount, sender_wallet_id, receiver_wallet_id)
VALUES (1, 2, 500.00, 1, 3),
       (2, 1, 1000.00, 2, 1),
       (3, 1, 50.00, 1, 2);

INSERT INTO transfers (transfer_request_id, status, transaction_date, sender_wallet_id, receiver_wallet_id)
VALUES (1, 'Pending', '2023-04-04 12:00:00', 1, 3),
       (2, 'SUCCESS', '2023-04-03 15:30:00', 2, 1),
       (3, 'FAILED', '2023-04-02 08:15:00', 1, 2);

