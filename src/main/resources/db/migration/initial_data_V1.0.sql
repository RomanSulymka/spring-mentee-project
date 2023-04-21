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
       ('Bob', 'Johnson', 'bobjohnson@example.com', 'password789');

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