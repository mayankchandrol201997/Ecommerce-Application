CREATE TABLE category
(
    id            BINARY(16)   NOT NULL,
    created_at    datetime     NULL,
    created_by    VARCHAR(255) NULL,
    updated_at    datetime     NULL,
    updated_by    VARCHAR(255) NULL,
    category_name VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BINARY(16)   NOT NULL,
    created_at    datetime     NULL,
    created_by    VARCHAR(255) NULL,
    updated_at    datetime     NULL,
    updated_by    VARCHAR(255) NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    image         VARCHAR(255) NULL,
    category_id   BINARY(16)   NULL,
    price         DOUBLE       NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);