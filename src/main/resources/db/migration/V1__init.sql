CREATE TABLE member
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NULL,
    email           VARCHAR(255)          NULL,
    picture_url     VARCHAR(255)          NULL,
    registration_id VARCHAR(255)          NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
);

ALTER TABLE member
    ADD CONSTRAINT unique_column_in_member UNIQUE (email);

CREATE TABLE refresh_token
(
    refresh_token_id VARCHAR(255) NOT NULL,
    expired_time     datetime     NOT NULL,
    member_id        BIGINT       NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (refresh_token_id)
);
