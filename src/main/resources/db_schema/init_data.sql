INSERT INTO currency (id, name, abbr)
VALUES
  (1, 'British Pound', 'GBP');

INSERT INTO transfer_status (id, name)
VALUES
       (1, 'Initiated'),
       (2, 'Processing'),
       (3, 'Failed'),
       (4, 'Successful');

INSERT INTO bank_account (owner_name, balance, blocked_amount, currency_id)
VALUES
  ('Swathi Angirekula', 1000.5, 0, 1),
  ('Eliyaz Shaik', 1000.5, 0, 1),
  ('Naushad Shaik', 1000.5, 0, 1);