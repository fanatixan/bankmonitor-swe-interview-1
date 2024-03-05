TRUNCATE TABLE transaction RESTART IDENTITY;
INSERT INTO transaction (amount, reference) VALUES
  (100, 'BM_2023_101'),
  (3333, ''),
  (-100, 'BM_2023_101_BACK'),
  (12345, 'BM_2023_105'),
  (54321, '');
