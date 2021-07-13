DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id VARCHAR(50) PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(500) NOT NULL,
  price DECIMAL(20,2) NOT NULL
);

INSERT INTO products (id, name, description, price) VALUES
('0001', 'Product 1', 'Description for product 1', 10),
('0002', 'Item    2', 'Description for product 2', 100),
('0003', 'Product 3', 'Description for item 3  ', 20),
('0004', 'Product 4', 'Description for product 4', 200),
('0005', 'Product 5', 'Description for product 5', 503.99);


