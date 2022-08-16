ALTER TABLE branch ADD UNIQUE (branch_name, repository_id);

ALTER TABLE commits ADD UNIQUE (sha);

ALTER TABLE deduplication_check_table ADD UNIQUE (cosmosdb_id);

ALTER TABLE deployment_workflow ADD UNIQUE (remote_identifier);

ALTER TABLE failed_change ADD UNIQUE (release_id, repository_id);

ALTER TABLE git_organization ADD UNIQUE (remote_identifier);

ALTER TABLE git_user ADD UNIQUE (remote_identifier);

ALTER TABLE issue ADD UNIQUE (repository_id, open_user_id);

ALTER TABLE issue_label ADD UNIQUE (label, issue_id, release_id);

ALTER TABLE pull_request ADD UNIQUE (remote_identifier);

ALTER TABLE pull_request_commit_table ADD UNIQUE (pull_request_id, commit_id);

ALTER TABLE pull_request_direction ADD UNIQUE (source_pull_request_id, outgoing_pull_request_id);

ALTER TABLE release ADD UNIQUE (remote_identifier);

ALTER TABLE release_event ADD UNIQUE (release_event_type, release_id);

ALTER TABLE repository ADD UNIQUE (remote_identifier);

ALTER TABLE workflow_run ADD UNIQUE (remote_identifier);