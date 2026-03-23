CREATE TABLE configuration_properties
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    application VARCHAR(255) NULL,
    profile     VARCHAR(255) NULL,
    label       VARCHAR(255) NULL,
    prop_key    VARCHAR(255) NULL,
    prop_value  VARCHAR(255) NULL,
    CONSTRAINT pk_configuration_properties PRIMARY KEY (id)
);