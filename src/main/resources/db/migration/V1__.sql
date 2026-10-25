CREATE TABLE cart_items
(
    customer_id       BINARY(16) NOT NULL,
    catalogue_item_id BINARY(16) NOT NULL,
    quantity          INT        NOT NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (customer_id, catalogue_item_id)
);

CREATE TABLE catalogue_items
(
    id    BINARY(16)    NOT NULL,
    name  VARCHAR(255)  NOT NULL,
    about VARCHAR(1000) NOT NULL,
    price DOUBLE        NOT NULL,
    CONSTRAINT pk_catalogue_items PRIMARY KEY (id)
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
    order_id          BINARY(16) NOT NULL,
    catalogue_item_id BINARY(16) NOT NULL,
    quantity          INT        NOT NULL,
    unit_price        DOUBLE     NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (order_id, catalogue_item_id)
);

CREATE TABLE orders
(
    id                        BINARY(16)   NOT NULL,
    customer_id               BINARY(16)   NOT NULL,
    shipping_address_snapshot VARCHAR(255) NOT NULL,
    created_at                datetime(6)  NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_CATALOGUE_ITEM FOREIGN KEY (catalogue_item_id) REFERENCES catalogue_items (id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_CATALOGUE_ITEM FOREIGN KEY (catalogue_item_id) REFERENCES catalogue_items (id);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);