CREATE TABLE IF NOT EXISTS transaction (
  id INT NOT NULL PRIMARY KEY,
  amount INT NOT NULL,
  reference VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL default NOW()
);