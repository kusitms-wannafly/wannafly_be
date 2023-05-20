ALTER TABLE application_item ADD category_id BIGINT;

CREATE INDEX idx_member_category ON category (member_id, category_id);
