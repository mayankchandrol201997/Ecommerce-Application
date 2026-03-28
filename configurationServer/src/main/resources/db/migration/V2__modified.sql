ALTER TABLE configuration_properties
    ADD is_encrypted BIT(1) NULL;

ALTER TABLE configuration_properties
    MODIFY is_encrypted BIT(1) NOT NULL;