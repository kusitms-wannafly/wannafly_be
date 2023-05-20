ALTER TABLE application_item ADD category_id BIGINT;

CREATE INDEX idx_category_of_item ON application_item (category_id);
