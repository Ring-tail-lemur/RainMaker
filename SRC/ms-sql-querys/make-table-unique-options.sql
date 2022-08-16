
ALTER TABLE branch ADD CONSTRAINT UNIQUE_name_AND_repository_id_TO_branch_1 UNIQUE ([name], repository_id);

ALTER TABLE commits ADD CONSTRAINT UNIQUE_sha_TO_commit_1 UNIQUE (sha);

ALTER TABLE deduplication_check_table ADD CONSTRAINT UNIQUE_cosmosdb_id_TO_deduplication_check_table_1 UNIQUE (cosmosdb_id);

ALTER TABLE deployment_workflow ADD CONSTRAINT UNIQUE_remote_identifier_TO_deployment_workflow_1 UNIQUE (remote_identifier);

ALTER TABLE failed_change ADD CONSTRAINT UNIQUE_release_id_AND_repository_id_TO_failed_change_1 UNIQUE (release_id, repository_id);

ALTER TABLE git_organization ADD CONSTRAINT UNIQUE_remote_identifier_TO_git_organization_1 UNIQUE (remote_identifier);

ALTER TABLE git_user ADD CONSTRAINT UNIQUE_remote_identifier_TO_git_user_1 UNIQUE (remote_identifier);

-- 이건 다시 생각해봐야함, 이거 두번 오면 무슨 문제가 발생할지는 뻔한데, 특정할 수 있나? name이 있어야 할 것 같다.
ALTER TABLE issue ADD CONSTRAINT UNIQUE_repository_id_AND_open_user_id_state_TO_issue_1 UNIQUE (repository_id, open_user_id,[state]);

ALTER TABLE issue_label ADD CONSTRAINT UNIQUE_label_AND_issue_id_release_id_TO_issue_label_1 UNIQUE (label, issue_id);


ALTER TABLE pull_request ADD CONSTRAINT UNIQUE_remote_identifier_TO_pull_request_1 UNIQUE (remote_identifier);

ALTER TABLE pull_request_commit_table ADD CONSTRAINT UNIQUE_pull_request_id_commit_id_TO_pull_request_commit_table_1 UNIQUE (pull_request_id, commit_id);

ALTER TABLE pull_request_direction ADD CONSTRAINT UNIQUE_source_pull_request_id_AND_outgoing_pull_request_id_TO_pull_request_direction_1 UNIQUE (source_pull_request_id, outgoing_pull_request_id);

ALTER TABLE release ADD CONSTRAINT UNIQUE_remote_identifier_TO_release_1 UNIQUE (remote_identifier);

-- 하나의 타입 이벤트가 하나의 릴리즈에 일어나는가?
ALTER TABLE release_event ADD CONSTRAINT UNIQUE_release_event_type_AND_release_id_TO_release_event_1 UNIQUE (release_event_type, release_id);

ALTER TABLE repository ADD CONSTRAINT UNIQUE_remote_identifier_TO_repository_1 UNIQUE (remote_identifier);

ALTER TABLE workflow_run ADD CONSTRAINT UNIQUE_remote_identifier_TO_workflow_run_1 UNIQUE (remote_identifier);