CREATE TABLE git_organization
(
    git_organization_id BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    [name]              VARCHAR(255) NOT NULL,
    created_date        DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date       DATETIME2    NOT NULL DEFAULT GETDATE()
);

CREATE TABLE repository
(
    repository_id         BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    [name]                VARCHAR(255) NOT NULL,
    owner_type            VARCHAR(20)  NOT NULL,
    created_date          DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date         DATETIME2    NOT NULL DEFAULT GETDATE(),
    owner_user_id         BIGINT,
    owner_organization_id BIGINT,
);

CREATE TABLE git_user
(
    git_user_id   BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    [name]        VARCHAR(255) NOT NULL,
    created_date  DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2    NOT NULL DEFAULT GETDATE()
);

CREATE TABLE pull_request
(
    pull_request_id                  BIGINT    NOT NULL PRIMARY KEY, --remote_identifier
    pull_request_number              BIGINT    NOT NULL,
    repository_id                    BIGINT    NOT NULL,
    pull_request_open_branch_id      VARCHAR(255)    NOT NULL,
    pull_request_close_branch_id     VARCHAR(255)    NOT NULL,
    created_date                     DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                    DATETIME2 NOT NULL DEFAULT GETDATE(),
    lead_time_for_change_process_end BIT       NOT NULL DEFAULT 0
);

CREATE TABLE pull_request_event
(
    pull_request_event_id   UNIQUEIDENTIFIER NOT NULL PRIMARY KEY, --X-GitHub-Delivery
    pull_request_event_type VARCHAR(50)      NOT NULL,
    event_time              DATETIME2        NOT NULL,
    pull_request_id         BIGINT           NOT NULL,
    event_sender_id         BIGINT,
    created_date            DATETIME2        NOT NULL DEFAULT GETDATE(),
    modified_date           DATETIME2        NOT NULL DEFAULT GETDATE()
);

CREATE TABLE user_organization_table -- 다대다 관계 테이블
(
    user_organization_table_id BIGINT PRIMARY KEY IDENTITY,
    member_id                  BIGINT    NOT NULL,
    git_organization_id        BIGINT    NOT NULL,
    created_date               DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date              DATETIME2 NOT NULL DEFAULT GETDATE()
);

CREATE TABLE pull_request_commit_table -- 다대다 관계 테이블
(
    pull_request_commit_table_id BIGINT PRIMARY KEY IDENTITY,
    pull_request_id              BIGINT    NOT NULL,
    commit_id                    varchar(255)    NOT NULL,
    first_commit                 BIT       NOT NULL,
    created_date                 DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                DATETIME2 NOT NULL DEFAULT GETDATE()
);

CREATE TABLE commits
(
    commit_id     varchar(255)  NOT NULL PRIMARY KEY, -- sha
    author_id     BIGINT,
    [message]     VARCHAR(1000) NOT NULL,
    created_date  DATETIME2     NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2     NOT NULL DEFAULT GETDATE(),
    commit_time   DATETIME2     NOT NULL,
    release_id    BIGINT
);

CREATE TABLE pull_request_comment
(
    pull_request_comment_id BIGINT      NOT NULL PRIMARY KEY, -- remote_identifier
    event_time              DATETIME2   NOT NULL,
    pull_request_id         BIGINT      NOT NULL,
    git_user_id             BIGINT,
    comment_type            VARCHAR(50) NOT NULL,
    created_date            DATETIME2   NOT NULL DEFAULT GETDATE(),
    modified_date           DATETIME2   NOT NULL DEFAULT GETDATE()
);

CREATE TABLE branch
(
    branch_id     UNIQUEIDENTIFIER NOT NULL PRIMARY KEY, -- Create 시점의 X-GitHub-Delivery
    [name]        VARCHAR(255)     NOT NULL,
    repository_id BIGINT           NOT NULL,
    git_user_id   BIGINT,
    created_date  DATETIME2        NOT NULL DEFAULT GETDATE(),
    modified_date DATETIME2        NOT NULL DEFAULT GETDATE(),
    is_closed     BIT              NOT NULL DEFAULT 0,
    create_type   VARCHAR(255)     NOT NULL DEFAULT 'BRANCH'
);

CREATE TABLE pull_request_direction -- 특수 로직
(
    pull_request_direction_id        BIGINT PRIMARY KEY IDENTITY,
    source_pull_request_id           BIGINT,
    outgoing_pull_request_id         BIGINT,
    created_date                     DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                    DATETIME2 NOT NULL DEFAULT GETDATE(),
    lead_time_for_change_process_end BIT       NOT NULL DEFAULT 0
);

CREATE TABLE issue
(
    issue_id                  BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    repository_id             BIGINT       NOT NULL,
    open_user_id              BIGINT       NOT NULL,
    created_date              DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date             DATETIME2    NOT NULL DEFAULT GETDATE(),
    [state]                   VARCHAR(255) NOT NULL,
    failed_change_process_end BIT                   DEFAULT 0,
    issue_label_id            BIGINT
);

CREATE TABLE issue_event
(
    issue_event_id   UNIQUEIDENTIFIER NOT NULL PRIMARY KEY, --X-GitHub-Delivery
    issue_event_type VARCHAR(255)     NOT NULL,
    event_time       DATETIME2        NOT NULL,
    event_sender_id  BIGINT,
    issue_id         BIGINT           NOT NULL,
    created_date     DATETIME2        NOT NULL DEFAULT GETDATE(),
    modified_date    DATETIME2        NOT NULL DEFAULT GETDATE()
);

CREATE TABLE issue_label
(
    issue_label_id BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    [label]        VARCHAR(255) NOT NULL,
    created_date   DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date  DATETIME2    NOT NULL DEFAULT GETDATE(),
    release_id     BIGINT,
    repository_id  BIGINT       NOT NULL
);

CREATE TABLE workflow_run
(
    workflow_run_id        BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
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

CREATE TABLE deployment_workflow
(
    deployment_workflow_id BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    [name]                 VARCHAR(225) NOT NULL,
    [path]                 VARCHAR(255) NOT NULL,
    created_date           DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date          DATETIME2    NOT NULL DEFAULT GETDATE(),
    issue_label_id         BIGINT       NULL
);

CREATE TABLE release
(
    release_id                       BIGINT       NOT NULL PRIMARY KEY, --remote_identifier
    pre_release                      BIT          NOT NULL,
    [name]                           VARCHAR(255) NOT NULL,
    author_id                        BIGINT,
    tag_name                         VARCHAR(255) NOT NULL,
    created_date                     DATETIME2    NOT NULL DEFAULT GETDATE(),
    modified_date                    DATETIME2    NOT NULL DEFAULT GETDATE(),
    repository_id                    BIGINT       NOT NULL,
    published_at                     DATETIME2    NULL,
    draft                            BIT          NOT NULL,
    lead_time_for_change_process_end BIT          NOT NULL DEFAULT 0,
    change_failure_rate_process_end  BIT          NOT NULL DEFAULT 0
);

CREATE TABLE release_event
(
    release_event_id   UNIQUEIDENTIFIER NOT NULL PRIMARY KEY, -- X-GitHub-Delivery
    release_event_type VARCHAR(255)     NOT NULL,
    release_id         BIGINT           NOT NULL,
    created_date       DATETIME2        NOT NULL DEFAULT GETDATE(),
    modified_date      DATETIME2        NOT NULL DEFAULT GETDATE()
);

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
    release_id              BIGINT    NULL
);

CREATE TABLE release_success
(
    release_success_id                  BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    release_id                          BIGINT    NOT NULL,
    created_date                        DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date                       DATETIME2 NOT NULL DEFAULT GETDATE(),
    first_error_issue_id                BIGINT,
    time_to_restore_service_process_end BIT       NOT NULL DEFAULT 0,
    repository_id                       BIGINT    NOT NULL,
    failed_at                           DATETIME2,
    is_success                          BIT       NOT NULL DEFAULT 1,
    released_at                         DATETIME2 NOT NULL
);

CREATE TABLE time_to_restore_service
(
    time_to_restore_service_id BIGINT    NOT NULL PRIMARY KEY IDENTITY,
    restore_service_time       BIGINT    NOT NULL,
    release_success_id         BIGINT    NOT NULL,
    created_date               DATETIME2 NOT NULL DEFAULT GETDATE(),
    modified_date              DATETIME2 NOT NULL DEFAULT GETDATE(),
    restored_at                DATETIME2 NOT NULL,
    repository_id              BIGINT    NOT NULL
);
