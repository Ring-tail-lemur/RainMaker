﻿DROP TABLE git_organization;
CREATE TABLE git_organization
(
    git_organization_id BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [name]              VARCHAR(255) NOT NULL,
    remote_identifier   BIGINT       NOT NULL,
    created_date        DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date       DATETIME2    NOT NULL DEFAULT GETDATE()
);

DROP TABLE repository;
CREATE TABLE repository
(
    repository_id         BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [name]                VARCHAR(255) NOT NULL,
    owner_type            VARCHAR(20)  NOT NULL,
    created_date          DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date         DATETIME2    NOT NULL DEFAULT GETDATE(),
    owner_user_id         BIGINT,
    owner_organization_id BIGINT,
    remote_identifier     BIGINT       NOT NULL unique
);

DROP TABLE git_user;
CREATE TABLE git_user
(
    git_user_id       BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [name]            VARCHAR(255) NOT NULL,
    remote_identifier BIGINT       NOT NULL unique,
    created_date      DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date     DATETIME2    NOT NULL DEFAULT GETDATE()
);

DROP TABLE pull_request;
CREATE TABLE pull_request
(
    pull_request_id              BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    remote_identifier            BIGINT    NOT NULL unique,
    pull_request_number          BIGINT    NOT NULL,
    repository_id                BIGINT    NOT NULL,
    pull_request_open_branch_id  BIGINT    NOT NULL,
    pull_request_close_branch_id BIGINT    NOT NULL,
    created_date                 DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                DATETIME2 NOT NULL DEFAULT GETDATE(),
    process_end                  BIT       NOT NULL DEFAULT 0
);

DROP TABLE pull_request_event;
CREATE TABLE pull_request_event
(
    pull_request_event_id   BIGINT      NOT NULL PRIMARY KEY IDENTITY,
    pull_request_event_type VARCHAR(50) NOT NULL,
    event_time              DATETIME2   NOT NULL,
    pull_request_id         BIGINT      NOT NULL,
    event_sender_id         BIGINT      NOT NULL,
    created_date            DATETIME2   NOT NULL DEFAULT GETDATE(),
    modified_date           DATETIME2   NOT NULL DEFAULT GETDATE()
);

DROP TABLE user_organization_table;
CREATE TABLE user_organization_table
(
    user_organization_table_id BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    member_id                  BIGINT    NOT NULL,
    git_organization_id        BIGINT    NOT NULL,
    created_date               DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date              DATETIME2 NOT NULL DEFAULT GETDATE()
);

DROP TABLE commits;
CREATE TABLE commits
(
    commit_id     BIGINT        NOT NULL PRIMARY KEY IDENTITY,
    sha           VARCHAR(40)   NOT NULL unique,
    author_id     BIGINT,
    [message]     VARCHAR(1000) NOT NULL,
    created_date  DATETIME2     NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2     NOT NULL DEFAULT GETDATE(),
    commit_time   DATETIME2     NOT NULL,
    release_id    BIGINT
);

DROP TABLE pull_request_commit_table;
CREATE TABLE pull_request_commit_table
(
    pull_request_commit_table_id BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    pull_request_id              BIGINT    NOT NULL,
    commit_id                    BIGINT    NOT NULL,
    first_commit                 BIT       NOT NULL,
    created_date                 DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                DATETIME2 NOT NULL DEFAULT GETDATE()
);

DROP TABLE pull_request_comment;
CREATE TABLE pull_request_comment
(
    pull_request_comment_id BIGINT      NOT NULL PRIMARY KEY IDENTITY,
    event_time              DATETIME2   NOT NULL,
    pull_request_id         BIGINT      NOT NULL,
    git_user_id             BIGINT,
    comment_type            VARCHAR(50) NOT NULL,
    created_date            DATETIME2   NOT NULL DEFAULT GETDATE(),
    modified_date           DATETIME2   NOT NULL DEFAULT GETDATE()
);

DROP TABLE branch;
CREATE TABLE branch
(
    branch_id     BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [name]        VARCHAR(255) NOT NULL,
    repository_id BIGINT       NOT NULL,
    git_user_id   BIGINT,
    created_date  DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2    NOT NULL DEFAULT GETDATE()
);

DROP TABLE lead_time_for_change;
CREATE TABLE lead_time_for_change
(
    lead_time_for_change_id BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    first_commit_time       DATETIME2,
    first_review_time       DATETIME2,
    pr_close_time           DATETIME2,
    pr_open_time            DATETIME2,
    deployment_time         DATETIME2,
    pull_request_id         BIGINT    NOT NULL,
    created_date            DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date           DATETIME2 NOT NULL DEFAULT GETDATE(),
    repository_id           BIGINT    NOT NULL,
    release_id     BIGINT    NULL
);

DROP TABLE pull_request_direction;
CREATE TABLE pull_request_direction
(
    pull_request_direction_id BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    source_pull_request_id    BIGINT,
    outgoing_pull_request_id  BIGINT,
    created_date              DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date             DATETIME2 NOT NULL DEFAULT GETDATE(),
    process_end               BIT       NOT NULL DEFAULT 0
);

DROP TABLE issue;
CREATE TABLE issue
(
    issue_id      BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    repository_id BIGINT       NOT NULL,
    open_user_id  BIGINT       NOT NULL,
    created_date  DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2    NOT NULL DEFAULT GETDATE(),
    [state]       VARCHAR(255) NOT NULL
);

DROP TABLE issue_event;
CREATE TABLE issue_event
(
    issue_event_id    BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    issue_event_type  VARCHAR(255) NOT NULL,
    event_time        DATETIME2    NOT NULL,
    event_sender_id   BIGINT       NOT NULL,
    issue_id          BIGINT       NOT NULL,
    created_date      DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date     DATETIME2    NOT NULL DEFAULT GETDATE(),
    remote_identifier BIGINT       NOT NULL unique
);

DROP TABLE issue_label;
CREATE TABLE issue_label
(
    issue_label_id BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [label]        VARCHAR(255) NOT NULL,
    issue_id       BIGINT       NOT NULL,
    created_date   DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date  DATETIME2    NOT NULL DEFAULT GETDATE()
);

DROP TABLE workflow_run;
CREATE TABLE workflow_run
(
    workflow_run_id        BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    remote_identifier      BIGINT       NOT NULL unique,
    deployment_workflow_id BIGINT       NOT NULL,
    run_number             BIGINT       NOT NULL,
    trigger_event          VARCHAR(255) NOT NULL,
    pull_request_id        BIGINT       NOT NULL,
    conclusion             VARCHAR(255) NOT NULL,
    created_date           DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date          DATETIME2    NOT NULL DEFAULT GETDATE(),
    process_end            BIT          NOT NULL DEFAULT 0,
    repository_id          BIGINT       NOT NULL,
    workflow_end_time      DATETIME2    NOT NULL
);

DROP TABLE deployment_workflow;
CREATE TABLE deployment_workflow
(
    deployment_workflow_id BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    [name]                 VARCHAR(225) NOT NULL,
    remote_identifier      BIGINT       NOT NULL unique,
    [path]                 VARCHAR(255) NOT NULL,
    created_date           DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date          DATETIME2    NOT NULL DEFAULT GETDATE(),
    issue_label_id         BIGINT       NULL
);

DROP TABLE release;
CREATE TABLE release
(
    release_id        BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    remote_identifier BIGINT       NOT NULL UNIQUE,
    pre_release       BIT          NOT NULL,
    [name]            VARCHAR(255) NOT NULL,
    author_id         BIGINT       NOT NULL,
    tag_id            BIGINT       NOT NULL,
    created_date      DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date     DATETIME2    NOT NULL DEFAULT GETDATE(),
    repository_id     BIGINT       NOT NULL,
    published_at      DATETIME2    NULL,
    draft             BIT          NOT NULL,
    process_end       BIT                   DEFAULT 0
);

DROP TABLE release_event;
CREATE TABLE release_event
(
    release_event_id   BIGINT       NOT NULL PRIMARY KEY IDENTITY,
    release_event_type VARCHAR(255) NOT NULL,
    release_id         BIGINT       NOT NULL,
    created_date       DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date      DATETIME2    NOT NULL DEFAULT GETDATE()
);


ALTER TABLE release
    ADD CONSTRAINT FK_git_user_TO_release_1 FOREIGN KEY (author_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE release
    ADD CONSTRAINT FK_branch_TO_release_1 FOREIGN KEY (tag_id)
        REFERENCES branch (branch_id);

ALTER TABLE release
    ADD CONSTRAINT FK_repository_TO_release_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

ALTER TABLE release_event
    ADD CONSTRAINT FK_release_TO_release_event_1 FOREIGN KEY (release_id)
        REFERENCES release (release_id);

ALTER TABLE workflow_run
    ADD CONSTRAINT FK_deployment_workflow_TO_workflow_run_1 FOREIGN KEY (deployment_workflow_id)
        REFERENCES deployment_workflow (deployment_workflow_id);

ALTER TABLE deployment_workflow
    ADD CONSTRAINT FK_issue_TO_deployment_workflow_1 FOREIGN KEY (issue_label_id)
        REFERENCES issue_label (issue_label_id);

ALTER TABLE workflow_run
    ADD CONSTRAINT FK_pull_request_TO_workflow_run_1 FOREIGN KEY (pull_request_id)
        REFERENCES pull_request (pull_request_id);

ALTER TABLE workflow_run
    ADD CONSTRAINT FK_repository_TO_workflow_run_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

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

ALTER TABLE user_organization_table
    ADD CONSTRAINT FK_git_user_TO_user_organization_table_1 FOREIGN KEY (member_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE user_organization_table
    ADD CONSTRAINT FK_git_organization_TO_user_organization_table_1 FOREIGN KEY (git_organization_id)
        REFERENCES git_organization (git_organization_id);

ALTER TABLE commits
    ADD CONSTRAINT FK_git_user_TO_commits_1 FOREIGN KEY (author_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE commits
    ADD CONSTRAINT FK_release_TO_commits_1 FOREIGN KEY (release_id)
        REFERENCES release (release_id);

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

ALTER TABLE lead_time_for_change
    ADD CONSTRAINT FK_repository_TO_lead_time_for_change_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

ALTER TABLE lead_time_for_change
    ADD CONSTRAINT FK_release_TO_lead_time_for_change_1 FOREIGN KEY (release_id)
        REFERENCES release (release_id);

ALTER TABLE branch
    ADD CONSTRAINT FK_branch_TO_repository_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

ALTER TABLE issue
    ADD CONSTRAINT FK_repository_TO_issue_1 FOREIGN KEY (repository_id)
        REFERENCES repository (repository_id);

ALTER TABLE issue
    ADD CONSTRAINT FK_git_user_TO_issue_1 FOREIGN KEY (open_user_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE issue_event
    ADD CONSTRAINT FK_git_user_TO_issue_event_1 FOREIGN KEY (event_sender_id)
        REFERENCES git_user (git_user_id);

ALTER TABLE issue_event
    ADD CONSTRAINT FK_issue_TO_issue_event_1 FOREIGN KEY (issue_id)
        REFERENCES issue (issue_id);

ALTER TABLE issue_label
    ADD CONSTRAINT FK_issue_TO_issue_label_1 FOREIGN KEY (issue_id)
        REFERENCES issue (issue_id);
