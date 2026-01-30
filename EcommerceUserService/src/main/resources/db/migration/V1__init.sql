CREATE TABLE `role`
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NULL,
    created_by VARCHAR(255) NULL,
    updated_at datetime     NULL,
    updated_by VARCHAR(255) NULL,
    `role`     VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE token
(
    id           BINARY(16)   NOT NULL,
    created_at   datetime     NULL,
    created_by   VARCHAR(255) NULL,
    updated_at   datetime     NULL,
    updated_by   VARCHAR(255) NULL,
    token        VARCHAR(255) NULL,
    expiring_at  datetime     NULL,
    token_status VARCHAR(255) NULL,
    user_id      BINARY(16)   NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BINARY(16)   NOT NULL,
    created_at datetime     NULL,
    created_by VARCHAR(255) NULL,
    updated_at datetime     NULL,
    updated_by VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BINARY(16) NOT NULL,
    roles_id BINARY(16) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, roles_id)
);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);