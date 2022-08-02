    DROP TABLE git_organization;
CREATE TABLE git_organization
(
    git_organization_id bigint          NOT NULL PRIMARY KEY IDENTITY,
    [name]              varchar(255) NOT NULL,
    remote_identifier   bigint          NOT NULL,
    created_date        datetime2    Not Null,
    modified_date       datetime2    Not Null
);

DROP TABLE repository;
CREATE TABLE repository
(
    repository_id         bigint          NOT NULL PRIMARY KEY IDENTITY,
    [name]                varchar(255) NOT NULL,
    owner_type            varchar(20)  NOT NULL,
    created_date          datetime2    Not Null,
    modified_date         datetime2    Not Null,
    owner_user_id         bigint,
    owner_organization_id bigint
);

DROP TABLE git_user;
CREATE TABLE git_user
(
    git_user_id       bigint          NOT NULL PRIMARY KEY IDENTITY,
    [name]            varchar(255) NOT NULL,
    remote_identifier bigint          NOT NULL,
    created_date      datetime2    Not Null,
    modified_date     datetime2    Not Null
);

DROP TABLE pull_request;
CREATE TABLE pull_request
(
    pull_request_id              bigint       NOT NULL PRIMARY KEY IDENTITY,
    remote_identifier            bigint       NOT NULL,
    pull_request_number          bigint       NOT NULL,
    repository_id                bigint       NOT NULL,
    pull_request_open_branch_id  bigint       NOT NULL,
    pull_request_close_branch_id bigint       NOT NULL,
    created_date                 datetime2 Not Null,
    modified_date                datetime2 Not Null,
    process_end                  bit       NOT NULL DEFAULT 0
);

DROP TABLE pull_request_event;
CREATE TABLE pull_request_event
(
    pull_request_event_id bigint         NOT NULL PRIMARY KEY IDENTITY,
    event_type            varchar(50) NOT NULL,
    event_time            datetime2   NOT NULL,
    pull_request_id       bigint         NOT NULL,
    event_sender_id       bigint         NOT NULL,
    created_date          datetime2   Not Null,
    modified_date         datetime2   Not Null
);

DROP TABLE deployment_event;
CREATE TABLE deployment_event
(
    deployment_event_id     bigint       NOT NULL PRIMARY KEY IDENTITY,
    remote_identifier       bigint       NULL,
    deployment_success_time datetime2 NULL,
    pull_request_id         bigint       NOT NULL,
    created_date            datetime2 Not Null,
    modified_date           datetime2 Not Null,
    process_end             bit       NOT NULL DEFAULT 0
);

DROP TABLE user_organization_table;
CREATE TABLE user_organization_table
(
    user_organization_table_id bigint       NOT NULL PRIMARY KEY IDENTITY,
    member_id                  bigint       NOT NULL,
    git_organization_id        bigint       NOT NULL,
    created_date               datetime2 Not Null,
    modified_date              datetime2 Not Null
);

DROP TABLE [commits];
CREATE TABLE [commits]
(
    commit_id     bigint           NOT NULL PRIMARY KEY IDENTITY,
    sha           varchar(40)      NOT NULL,
    author_id     bigint           NOT NULL,
    [message]     varchar(1000) NOT NULL,
    created_date  datetime2     Not Null,
    modified_date datetime2     Not Null,
    commit_time   datetime2     Not Null
);

DROP TABLE pull_request_commit_table;
CREATE TABLE pull_request_commit_table
(
    pull_request_commit_table_id bigint       NOT NULL PRIMARY KEY IDENTITY,
    pull_request_id              bigint       NOT NULL,
    commit_id                    bigint       NOT NULL,
    first_commit                 bit       NOT NULL,
    created_date                 datetime2 Not Null,
    modified_date                datetime2 Not Null
);

DROP TABLE pull_request_comment;
CREATE TABLE pull_request_comment
(
    pull_request_comment_id bigint         NOT NULL PRIMARY KEY IDENTITY,
    event_time              datetime2   NOT NULL,
    pull_request_id         bigint         NOT NULL,
    git_user_id             bigint         NOT NULL,
    comment_type            varchar(50) NOT NULL,
    created_date            datetime2   Not Null,
    modified_date           datetime2   Not Null
);

DROP TABLE branch;
CREATE TABLE branch
(
    branch_id     bigint          NOT NULL PRIMARY KEY IDENTITY,
    [name]        varchar(255) NOT NULL,
    repository_id bigint          NOT NULL,
    git_user_id   bigint          NOT NULL,
    created_date  datetime2    Not Null,
    modified_date datetime2    Not Null
);

DROP TABLE lead_time_for_change;
CREATE TABLE lead_time_for_change
(
    lead_time_for_change_id bigint       NOT NULL PRIMARY KEY IDENTITY,
    total_time              bigint       NOT NULL DEFAULT 0,
    coding_time             bigint       NOT NULL DEFAULT 0,
    pickup_time             bigint       NOT NULL DEFAULT 0,
    review_time             bigint       NOT NULL DEFAULT 0,
    deploy_time             bigint       NOT NULL DEFAULT 0,
    pull_request_id         bigint       NOT NULL,
    created_date            datetime2 Not Null,
    modified_date           datetime2 Not Null
);

DROP TABLE pull_request_direction;
CREATE TABLE pull_request_direction
(
    pull_request_direction_id bigint       NOT NULL PRIMARY KEY IDENTITY,
    source_pull_request_id    bigint,
    outgoing_pull_request_id  bigint,
    created_date              datetime2 Not Null,
    modified_date             datetime2 Not Null
);

ALTER TABLE pull_request_direction
    ADD CONSTRAINT FK_pull_request_TO_pull_request_direction_1 FOREIGN KEY (source_pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_direction
    ADD CONSTRAINT FK_pull_request_TO_pull_request_direction_2 FOREIGN KEY (outgoing_pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE repository
    ADD CONSTRAINT FK_git_organization_TO_repository_1 FOREIGN KEY (owner_organization_id)
        REFERENCES git_organization (git_organization_id);

ALTER TABLE repository
    ADD CONSTRAINT FK_git_user_TO_repository_1 FOREIGN KEY (owner_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE pull_request
    ADD CONSTRAINT FK_repository_TO_pull_request_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);


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
    ADD CONSTRAINT FK_git_user_TO_user_organization_table_1 FOREIGN KEY (member_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE user_organization_table
    ADD CONSTRAINT FK_git_organization_TO_user_organization_table_1 FOREIGN KEY (git_organization_id)
        REFERENCES git_organization (git_organization_id);

ALTER TABLE commits
    ADD CONSTRAINT FK_git_user_TO_commit_1 FOREIGN KEY (author_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE pull_request_commit_table
    ADD CONSTRAINT FK_pull_request_TO_pull_request_commit_table_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_commit_table
    ADD CONSTRAINT FK_commit_TO_pull_request_commit_table_1 FOREIGN KEY (commit_id)
        REFERENCES commits (commit_id);

ALTER TABLE pull_request_comment
    ADD CONSTRAINT FK_pull_request_TO_pull_request_comment_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE pull_request_comment
    ADD CONSTRAINT FK_git_user_TO_pull_request_comment_1 FOREIGN KEY (git_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE branch
    ADD CONSTRAINT FK_git_user_TO_branch_1 FOREIGN KEY (git_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE lead_time_for_change
    ADD CONSTRAINT FK_pull_request_TO_lead_time_for_change_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE branch
    ADD CONSTRAINT FK_branch_TO_repository_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);
