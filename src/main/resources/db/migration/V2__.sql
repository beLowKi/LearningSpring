ALTER TABLE cart_items
    DROP FOREIGN KEY FK_CART_ITEMS_ON_CATALOGUE_ITEM;

ALTER TABLE order_items
    DROP FOREIGN KEY FK_ORDER_ITEMS_ON_CATALOGUE_ITEM;

CREATE TABLE categories
(
    id   BINARY(16)   NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE product_category
(
    category_id BINARY(16) NOT NULL,
    product_id  BINARY(16) NOT NULL,
    CONSTRAINT pk_product_category PRIMARY KEY (category_id, product_id)
);

CREATE TABLE products
(
    id    BINARY(16)    NOT NULL,
    name  VARCHAR(255)  NOT NULL,
    about VARCHAR(1000) NOT NULL,
    price DOUBLE        NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_name UNIQUE (name);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_CATALOGUE_ITEM FOREIGN KEY (catalogue_item_id) REFERENCES products (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_CATALOGUE_ITEM FOREIGN KEY (catalogue_item_id) REFERENCES products (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_product FOREIGN KEY (product_id) REFERENCES products (id);

DROP TABLE catalogue_items;