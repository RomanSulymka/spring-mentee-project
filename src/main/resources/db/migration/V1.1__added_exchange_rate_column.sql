-- Add_exchange_rate_to_money_table.sql

ALTER TABLE money
ADD COLUMN exchange_rate DECIMAL (10, 2);
