INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok1', 123456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok2', 223456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok3', 323456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok4', 423456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok5', 523456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok6', 623456, GETDATE(), GETDATE());

INSERT INTO git_user (name, remote_identifier, created_date, modified_date)
VALUES ('inhyeok7', 723456, GETDATE(), GETDATE());


INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org1', 1256342, GETDATE(), GETDATE());

INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org2', 2256342, GETDATE(), GETDATE());

INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org3', 3256342, GETDATE(), GETDATE());

INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org4', 4256342, GETDATE(), GETDATE());

INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org5', 5256342, GETDATE(), GETDATE());

INSERT INTO git_organization (name, remote_identifier, created_date, modified_date)
VALUES ('org6', 6256342, GETDATE(), GETDATE());


INSERT INTO user_organization_table (member_id, git_organization_id, created_date, modified_date)
VALUES (1, 1, GETDATE(), GETDATE());

INSERT INTO user_organization_table (member_id, git_organization_id, created_date, modified_date)
VALUES (2, 1, GETDATE(), GETDATE());

INSERT INTO user_organization_table (member_id, git_organization_id, created_date, modified_date)
VALUES (3, 2, GETDATE(), GETDATE());

INSERT INTO user_organization_table (member_id, git_organization_id, created_date, modified_date)
VALUES (4, 2, GETDATE(), GETDATE());


INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo1', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 1);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo2', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 1);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo3', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 1);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo4', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 2);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo5', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 2);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo6', 'ORGANIZATION', GETDATE(), GETDATE(), NULL, 2);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo7', 'USER', GETDATE(), GETDATE(), 1, NULL);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo8', 'USER', GETDATE(), GETDATE(), 2, NULL);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo10', 'USER', GETDATE(), GETDATE(), 3, NULL);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo11', 'USER', GETDATE(), GETDATE(), 4, NULL);

INSERT INTO repository (name, owner_type, created_date, modified_date, owner_user_id, owner_organization_id)
VALUES ('repo12', 'USER', GETDATE(), GETDATE(), 5, NULL);


INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch1', 1, 2, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch2', 1, 1, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch3', 1, 2, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch4', 1, 4, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch5', 1, 3, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch6', 1, 3, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('branch7', 1, 4, GETDATE(), GETDATE());

INSERT INTO branch (name, repository_id, git_user_id, created_date, modified_date)
VALUES ('main', 2, 3, GETDATE(), GETDATE());


INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (184515, 1, 1, 1, 2, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (284515, 2, 1, 2, 3, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (384515, 3, 1, 1, 3, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (484515, 4, 1, 2, 3, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (584515, 5, 1, 3, 4, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (684515, 6, 1, 2, 6, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (784515, 7, 1, 1, 5, GETDATE(), GETDATE());

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id, created_date, modified_date)
VALUES (784515, 8, 1, 5, 8, GETDATE(), GETDATE());


INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id, created_date, modified_date)
VALUES (1, 2, GETDATE(), GETDATE())

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id, created_date, modified_date)
VALUES (2, 5, GETDATE(), GETDATE())

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id, created_date, modified_date)
VALUES (3, 5, GETDATE(), GETDATE())

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id, created_date, modified_date)
VALUES (4, 5, GETDATE(), GETDATE())

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id, created_date, modified_date)
VALUES (7, 8, GETDATE(), GETDATE())


INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -7, GETDATE()), 1, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -6, GETDATE()), 2, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -5, GETDATE()), 3, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -4, GETDATE()), 4, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -3, GETDATE()), 5, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -2, GETDATE()), 6, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', DATEADD(DAY, -1, GETDATE()), 7, 1, GETDATE(), GETDATE())

INSERT INTO pull_request_event(event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
VALUES ('CLOSED', GETDATE(), 8, 1, GETDATE(), GETDATE())


INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -8, GETDATE()), 1, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -7, GETDATE()), 2, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -6, GETDATE()), 3, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -5, GETDATE()), 4, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -4, GETDATE()), 5, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -3, GETDATE()), 6, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -2, GETDATE()), 7, 2, 'idontknow', GETDATE(), GETDATE());

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type, created_date, modified_date)
VALUES (DATEADD(DAY, -1, GETDATE()), 8, 2, 'idontknow', GETDATE(), GETDATE());



INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -10, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -10, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -9, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -9, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -8, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -8, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -7, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -7, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -6, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -6, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -5, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -5, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -4, GETDATE()));

INSERT INTO [commit](sha, author_id, message, created_date, modified_date, commit_time)
VALUES ('ASDFWQA23', 1, 'ASDFASDF', GETDATE(), GETDATE(), DATEADD(DAY, -4, GETDATE()));


INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (1, 1, 1, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (3, 2, 1, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (1, 3, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (1, 4, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (3, 5, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (4, 6, 1, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (6, 7, 1, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (4, 8, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (4, 9, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (6, 10, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (6, 11, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (7, 12, 1, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (7, 13, 0, GETDATE(), GETDATE());

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit, created_date, modified_date)
VALUES (7, 14, 0, GETDATE(), GETDATE());


INSERT INTO deployment_event(remote_identifier, deployment_success_time, pull_request_id, created_date, modified_date)
VALUES (13421234, DATEADD(DAY, 1, GETDATE()), 8, GETDATE(), GETDATE());
