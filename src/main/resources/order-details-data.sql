INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (100, 'Munich', '1000.45', 'John', 'Roy', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (101, 'Munich', '1000.40', 'John101', 'Roy101', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (102, 'Munich', '1010.45', 'John102', 'Roy102', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (103, 'Munich', '10070.45', 'John103', 'Roy103', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (104, 'Munich', '1123.45', 'John104', 'Roy104', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (105, 'Munich', '456.45', 'John105', 'Roy105', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (201, 'Munich', '345.45', 'John106', 'Roy106', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (202, 'Munich', '567.45', 'John107', 'Roy107', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (203, 'Munich', '2344.45', 'John108', 'Roy108', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (204, 'Munich', '555.45', 'John109', 'Roy109', '123456789', '80687');

INSERT INTO customer (id, city, customer_balance, first_name, last_name, phone_number, post_code) VALUES (205, 'Munich', '4444.45', 'John110', 'Roy110', '123456789', '80687');


INSERT INTO product (id, product_description, product_name, product_price) VALUES (1001, 'DogFood', 'Dog_Can', '125.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1002, 'DogFood_1002', 'Dog_Can_1002', '3245.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1003, 'DogFood_1003', 'Dog_Can_1003', '4343.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1004, 'DogFood_1004', 'Dog_Can_1004', '324.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1005, 'DogFood_1005', 'Dog_Can_1005', '3256.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1006, 'DogFood_1006', 'Dog_Can_1006', '3289.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1007, 'DogFood_1007', 'Dog_Can_1007', '3290.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1008, 'DogFood_1008', 'Dog_Can_1008', '5677.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1009, 'DogFood_1009', 'Dog_Can_1009', '3455.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1010, 'DogFood_1010', 'Dog_Can_1010', '3245.56');

INSERT INTO product (id, product_description, product_name, product_price) VALUES (1012, 'DogFood_1020', 'Dog_Can_1020', '4567.56');


INSERT INTO order_details (id, comments, customer_id, order_date, order_status, product_id, products_prices_amount, total_product_amount) VALUES ('10001', 'Order Created Sucessfully', '201', '2022-02-28 03:07:29', '1', '1001,1002,1003,1004,1005', '11295.80', '18273.36');

INSERT INTO order_details (id, comments, customer_id, order_date, order_status, product_id, products_prices_amount, total_product_amount) VALUES ('10002', 'Order Created Sucessfully', '202', '2022-02-28 03:07:29', '1', '1001,1002,1003,1004,1005', '11295.80', '18273.36');

INSERT INTO order_details (id, comments, customer_id, order_date, order_status, product_id, products_prices_amount, total_product_amount) VALUES ('10003', 'Order Created Sucessfully', '203', '2022-02-28 03:07:29', '1', '1001,1002,1003,1004', '112525.80', '18273.36');


INSERT INTO payment (id,customer_id, order_balance, order_id, paid_amount, payment_date, payment_mode, products_prices_amount) VALUES ('20000', '201', '68704.20', '10000', '80000.00', '2022-02-28 00:53:27', 'Credit Card', '11295.80');

INSERT INTO payment (id,customer_id, order_balance, order_id, paid_amount, payment_date, payment_mode, products_prices_amount) VALUES ('20001', '202', '68704.20', '10002', '80000.00', '2022-02-28 00:53:27', 'Credit Card', '11295.80');

INSERT INTO payment (id,customer_id, order_balance, order_id, paid_amount, payment_date, payment_mode, products_prices_amount) VALUES ('20003', '203', '68704.20', '10003', '80000.00', '2022-02-28 00:53:27', 'Credit Card', '11295.80');