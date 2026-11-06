CREATE TABLE reviews
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    customer_id BINARY(16)            NULL,
    product_id  BINARY(16)            NOT NULL,
    rating      DOUBLE                NOT NULL,
    title       VARCHAR(255)          NOT NULL,
    message     VARCHAR(1000)         NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);