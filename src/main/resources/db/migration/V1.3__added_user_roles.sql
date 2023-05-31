-- Add role user_account table.sql

ALTER TABLE user_accounts
    ADD COLUMN role VARCHAR(50);