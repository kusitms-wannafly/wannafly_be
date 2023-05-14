CREATE TABLE application_folder
(
    application_folder_id BIGINT AUTO_INCREMENT NOT NULL,
    member_id           BIGINT                NOT NULL,
    years               INT                   NOT NULL,
    CONSTRAINT pk_applicationfolder PRIMARY KEY (application_folder_id)
);
