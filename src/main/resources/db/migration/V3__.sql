ALTER TABLE reviews
    DROP COLUMN id;

ALTER TABLE reviews
    MODIFY customer_id BINARY(16) NOT NULL;

ALTER TABLE reviews
    ADD PRIMARY KEY (customer_id, product_id);