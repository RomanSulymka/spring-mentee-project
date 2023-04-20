CREATE TABLE tokens
(
    id              BIGSERIAL PRIMARY KEY,
    token           VARCHAR(250),
    token_type      VARCHAR(50) NOT NULL,
    revoked         BOOLEAN         NOT NULL,
    expired         BOOLEAN         NOT NULL,
    user_account_id BIGINT,
    FOREIGN KEY (user_account_id) REFERENCES user_accounts (id)
);
