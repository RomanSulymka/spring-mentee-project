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

