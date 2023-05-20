CREATE TABLE category
(
    category_id BIGINT AUTO_INCREMENT NOT NULL,
    member_id   BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id)
);

CREATE INDEX idx_category_member_id ON category (member_id);
