-- Add role user_account table.sql

ALTER TABLE user_accounts
    ADD COLUMN role VARCHAR(50);

UPDATE user_accounts
SET role = 'ADMIN'
WHERE id = 4;
