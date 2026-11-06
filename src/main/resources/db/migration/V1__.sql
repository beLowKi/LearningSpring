CREATE TABLE cart_items
(
    customer_id BINARY(16) NOT NULL,
    product_id  BINARY(16) NOT NULL,
    quantity    INT        NOT NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (customer_id, product_id)
);

CREATE TABLE categories
(
    id   BINARY(16)   NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id       BINARY(16)   NOT NULL,
    name     VARCHAR(255) NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    address  VARCHAR(255) NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE order_items
(
    order_id   BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    quantity   INT        NOT NULL,
    unit_price DOUBLE     NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (order_id, product_id)
);

CREATE TABLE orders
(
    id                        BINARY(16)   NOT NULL,
    customer_id               BINARY(16)   NOT NULL,
    shipping_address_snapshot VARCHAR(255) NOT NULL,
    created_at                datetime(6)  NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE product_categories
(
    category_id BINARY(16) NOT NULL,
    product_id  BINARY(16) NOT NULL,
    CONSTRAINT pk_product_categories PRIMARY KEY (category_id, product_id)
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
    ADD CONSTRAINT FK_CART_ITEMS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_product FOREIGN KEY (product_id) REFERENCES products (id);