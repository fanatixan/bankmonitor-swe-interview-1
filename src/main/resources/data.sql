DELETE FROM transaction;
INSERT INTO transaction (id, amount, reference, created_at) VALUES
  (1, 100, 'BM_2023_101', NOW()),
  (2, 3333, '', NOW()),
  (3, -100, 'BM_2023_101_BACK', NOW()),
  (4, 12345, 'BM_2023_105', NOW()),
  (5, 54321, '', NOW());