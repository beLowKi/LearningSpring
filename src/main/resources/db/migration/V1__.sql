CREATE TABLE product_categories
(
    category_id BINARY(16) NOT NULL,
    product_id  BINARY(16) NOT NULL,
    CONSTRAINT pk_product_categories PRIMARY KEY (category_id, product_id)
);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_product FOREIGN KEY (product_id) REFERENCES products (id);

DROP TABLE catalogue_items;

DROP TABLE product_category;