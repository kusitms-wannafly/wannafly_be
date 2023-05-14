CREATE TABLE application_form
(
    application_form_id BIGINT AUTO_INCREMENT NOT NULL,
    member_id           BIGINT                NOT NULL,
    writing_state       VARCHAR(255)          NOT NULL,
    recruiter           VARCHAR(255)          NOT NULL,
    last_modified_time  datetime              NOT NULL,
    years               INT                   NOT NULL,
    semester            VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_applicationform PRIMARY KEY (application_form_id)
);

CREATE INDEX idx_writer ON application_form (member_id);

CREATE TABLE application_item
(
    application_item_id  BIGINT AUTO_INCREMENT NOT NULL,
    application_form_id  BIGINT                NULL,
    application_question LONGTEXT              NOT NULL,
    application_answer   LONGTEXT              NOT NULL,
    CONSTRAINT pk_applicationitem PRIMARY KEY (application_item_id)
);

ALTER TABLE application_item
    ADD CONSTRAINT FK_APPLICATIONITEM_ON_APPLICATION_FORM FOREIGN KEY (application_form_id) REFERENCES application_form (application_form_id);

