ALTER TABLE application_item ADD category_id BIGINT;

CREATE INDEX idx_category_of_item ON category (category_id);
