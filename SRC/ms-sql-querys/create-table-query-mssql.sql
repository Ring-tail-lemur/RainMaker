DROP TABLE git_organization;
CREATE TABLE git_organization
(
    git_organization_id int          NOT NULL PRIMARY KEY,
    [name]              varchar(255) NOT NULL,
    remote_identifier   int          NOT NULL,
    create_time         datetime     Not Null,
    modified_time       datetime     Not Null
);

DROP TABLE repository;
CREATE TABLE repository
(
    repository_id             int          NOT NULL PRIMARY KEY,
    [name]                    varchar(255) NOT NULL,
    repository_owner_table_id int          NOT NULL,
    create_time               datetime     Not Null,
    modified_time             datetime     Not Null
);

DROP TABLE git_user;
CREATE TABLE git_user
(
    git_user_id       int          NOT NULL PRIMARY KEY,
    [name]            varchar(255) NOT NULL,
    remote_identifier int          NOT NULL,
    create_time       datetime     Not Null,
    modified_time     datetime     Not Null
);

DROP TABLE repository_owner_table;
CREATE TABLE repository_owner_table
(
    repository_owner_table_id int         NOT NULL PRIMARY KEY,
    owner_type                varchar(20) NOT NULL,
    owner_user_id             int         NULL,
    git_organization_id       int         NULL,
    create_time               datetime    Not Null,
    modified_time             datetime    Not Null
);

DROP TABLE pull_request;
CREATE TABLE pull_request
(
    pull_request_id              int      NOT NULL PRIMARY KEY,
    remote_identifier            int      NOT NULL,
    pull_request_number          int      NOT NULL,
    repository_id                int      NOT NULL,
    parent_pull_request_id       int      NOT NULL,
    pull_request_open_branch_id  int      NOT NULL,
    pull_request_close_branch_id int      NOT NULL,
    create_time                  datetime Not Null,
    modified_time                datetime Not Null
);

DROP TABLE pull_request_event;
CREATE TABLE pull_request_event
(
    pull_request_event_id int         NOT NULL PRIMARY KEY,
    event_type            varchar(50) NOT NULL,
    event_time            datetime    NOT NULL,
    pull_request_id       int         NOT NULL,
    event_sender_id       int         NOT NULL,
    create_time           datetime    Not Null,
    modified_time         datetime    Not Null
);

DROP TABLE deployment_event;
CREATE TABLE deployment_event
(
    deployment_event_id     int      NOT NULL PRIMARY KEY,
    remote_identifier       int      NULL,
    deployment_success_time datetime NULL,
    pull_request_id         int      NOT NULL,
    create_time             datetime Not Null,
    modified_time           datetime Not Null
);

DROP TABLE user_organization_table;
CREATE TABLE user_organization_table
(
    user_organization_table_id int      NOT NULL PRIMARY KEY,
    git_user_id                int      NOT NULL,
    git_organization_id        int      NOT NULL,
    create_time                datetime Not Null,
    modified_time              datetime Not Null
);

DROP TABLE [commit];
CREATE TABLE [commit]
(
    commit_id     int           NOT NULL PRIMARY KEY,
    sha           char(40)      NOT NULL,
    author_id     int           NOT NULL,
    [message]     varchar(1000) NOT NULL,
    create_time   datetime      Not Null,
    modified_time datetime      Not Null
);

DROP TABLE pull_request_commit_table;
CREATE TABLE pull_request_commit_table
(
    pull_request_commit_table_id int      NOT NULL PRIMARY KEY,
    pull_request_id              int      NOT NULL,
    commit_id                    int      NOT NULL,
    first_commit                 bit      NOT NULL,
    create_time                  datetime Not Null,
    modified_time                datetime Not Null
);

DROP TABLE pull_request_comment;
CREATE TABLE pull_request_comment
(
    pull_request_comment_id int         NOT NULL PRIMARY KEY,
    event_time              datetime    NOT NULL,
    pull_request_id         int         NOT NULL,
    git_user_id             int         NOT NULL,
    comment_type            varchar(50) NOT NULL,
    create_time             datetime    Not Null,
    modified_time           datetime    Not Null
);

DROP TABLE branch;
CREATE TABLE branch
(
    branch_id                 int          NOT NULL PRIMARY KEY,
    [name]                    varchar(255) NOT NULL,
    repository_owner_table_id int          NOT NULL,
    git_user_id               int          NOT NULL,
    create_time               datetime     Not Null,
    modified_time             datetime     Not Null
);

DROP TABLE lead_time_for_change;
CREATE TABLE lead_time_for_change
(
    lead_time_for_change_id int      NOT NULL PRIMARY KEY,
    total_time              int      NOT NULL DEFAULT 0,
    coding_time             int      NOT NULL DEFAULT 0,
    pickup_time             int      NOT NULL DEFAULT 0,
    review_time             int      NOT NULL DEFAULT 0,
    deploy_time             int      NOT NULL DEFAULT 0,
    pull_request_id         int      NOT NULL,
    create_time             datetime Not Null,
    modified_time           datetime Not Null
);


ALTER TABLE repository
    ADD CONSTRAINT FK_repository_owner_table_TO_repository_1 FOREIGN KEY (repository_owner_table_id)
        REFERENCES repository_owner_table (repository_owner_table_id);

ALTER TABLE repository_owner_table
    ADD CONSTRAINT FK_git_user_TO_repository_owner_table_1 FOREIGN KEY (owner_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE repository_owner_table
    ADD CONSTRAINT FK_git_organization_TO_repository_owner_table_1 FOREIGN KEY (git_organization_id)
        REFERENCES git_organization (git_organization_id);

ALTER TABLE pull_request
    ADD CONSTRAINT FK_repository_TO_pull_request_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

ALTER TABLE pull_request
    ADD CONSTRAINT FK_pull_request_TO_pull_request_1 FOREIGN KEY (parent_pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request
    ADD CONSTRAINT FK_branch_TO_pull_request_1 FOREIGN KEY (pull_request_open_branch_id)
        REFERENCES branch (branch_id);

ALTER TABLE pull_request
    ADD CONSTRAINT FK_branch_TO_pull_request_2 FOREIGN KEY (pull_request_close_branch_id)
        REFERENCES branch (branch_id);

ALTER TABLE pull_request_event
    ADD CONSTRAINT FK_pull_request_TO_pull_request_event_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_event
    ADD CONSTRAINT FK_git_user_TO_pull_request_event_1 FOREIGN KEY (event_sender_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE deployment_event
    ADD CONSTRAINT FK_pull_request_TO_deployment_event_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE user_organization_table
    ADD CONSTRAINT FK_git_user_TO_user_organization_table_1 FOREIGN KEY (git_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE user_organization_table
    ADD CONSTRAINT FK_git_organization_TO_user_organization_table_1 FOREIGN KEY (git_organization_id)
        REFERENCES git_organization (git_organization_id);

ALTER TABLE [commit]
    ADD CONSTRAINT FK_git_user_TO_commit_1 FOREIGN KEY (author_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE pull_request_commit_table
    ADD CONSTRAINT FK_pull_request_TO_pull_request_commit_table_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_commit_table
    ADD CONSTRAINT FK_commit_TO_pull_request_commit_table_1 FOREIGN KEY (commit_id)
        REFERENCES [commit] (commit_id);

ALTER TABLE pull_request_comment
    ADD CONSTRAINT FK_pull_request_TO_pull_request_comment_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_comment
    ADD CONSTRAINT FK_git_user_TO_pull_request_comment_1 FOREIGN KEY (git_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE branch
    ADD CONSTRAINT FK_repository_owner_table_TO_branch_1 FOREIGN KEY (repository_owner_table_id)
        REFERENCES repository_owner_table (repository_owner_table_id);

ALTER TABLE branch
    ADD CONSTRAINT FK_git_user_TO_branch_1 FOREIGN KEY (git_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE lead_time_for_change
    ADD CONSTRAINT FK_pull_request_TO_lead_time_for_change_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);
