alter table transfer_requests
    alter column amount type NUMERIC using amount::NUMERIC;