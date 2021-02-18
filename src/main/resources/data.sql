INSERT INTO OrderApi.users(USERNAME, PASSWORD) VALUES('pmoran', '$2a$10$tW8Gl8cWEyMDNQ3La1/7g.mY03fRUVphDc8HraALCka4Gk68r7rLG');

INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 1', 100);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 2', 200);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 3', 300);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 4', 400);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 5', 500);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 6', 600);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 7', 700);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 8', 800);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 9', 900);
INSERT INTO OrderApi.products(NAME, PRICE) VALUES('Product 10', 1000);

INSERT INTO OrderApi.orders(reg_date, total) VALUES(now(), 1500);
INSERT INTO OrderApi.order_lines(fk_order, fk_product, price, quantity, total) VALUES(1, 1, 100, 1, 100);
INSERT INTO OrderApi.order_lines(fk_order, fk_product, price, quantity, total) VALUES(1, 2, 200, 1, 200);
INSERT INTO OrderApi.order_lines(fk_order, fk_product, price, quantity, total) VALUES(1, 3, 300, 1, 300);
INSERT INTO OrderApi.order_lines(fk_order, fk_product, price, quantity, total) VALUES(1, 4, 400, 1, 400);
INSERT INTO OrderApi.order_lines(fk_order, fk_product, price, quantity, total) VALUES(1, 5, 500, 1, 500);