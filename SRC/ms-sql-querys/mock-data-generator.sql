INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok1', 123456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok2', 223456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok3', 323456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok4', 423456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok5', 523456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok6', 623456);

INSERT INTO git_user (name, remote_identifier)
VALUES ('inhyeok7', 723456);


INSERT INTO git_organization (name, remote_identifier)
VALUES ('org1', 1256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('org2', 2256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('org3', 3256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('org4', 4256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('org5', 5256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('org6', 6256342);

INSERT INTO git_organization (name, remote_identifier)
VALUES ('Ring-tail-lemur', 107110653);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (1, 1);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (2, 1);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (3, 2);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (4, 2);


INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo1', 'ORGANIZATION', NULL, 1, 23564432);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo2', 'ORGANIZATION', NULL, 1, 23541432);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo3', 'ORGANIZATION', NULL, 1, 33565432);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo4', 'ORGANIZATION', NULL, 2, 43565432);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo5', 'ORGANIZATION', NULL, 2, 53565432);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo6', 'ORGANIZATION', NULL, 2, 63565432);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo7', 'USER', 1, NULL, 83565432);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo8', 'USER', 2, NULL, 23543232);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('repo10', 'USER', 3, NULL, 23562632);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('RainMaker', 'ORGANIZATION', 4, NULL, 517528822);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, remote_identifier)
VALUES ('test-for-fake-project', 'ORGANIZATION', 5, NULL, 510731046);


INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch1', 1, 2);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch2', 1, 1);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch3', 1, 2);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch4', 1, 4);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch5', 1, 3);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch6', 1, 3);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('branch7', 1, 4);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('main', 10, 1);

INSERT INTO branch (name, repository_id, git_user_id)
VALUES ('main', 11, 1);


INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (154515, 1, 1, 1, 2);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (284515, 2, 1, 2, 3);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (384515, 3, 1, 1, 3);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (484535, 4, 1, 2, 3);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (584515, 5, 1, 3, 4);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (684585, 6, 1, 2, 6);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (784515, 7, 1, 1, 5);

INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (784325, 8, 1, 5, 8);


INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id)
VALUES (1, 2)

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id)
VALUES (2, 5)

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id)
VALUES (3, 5)

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id)
VALUES (4, 5)

INSERT INTO pull_request_direction(source_pull_request_id, outgoing_pull_request_id)
VALUES (5, 8)


INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -7, GETDATE()), 1, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -6, GETDATE()), 2, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -5, GETDATE()), 3, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -4, GETDATE()), 4, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -3, GETDATE()), 5, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -2, GETDATE()), 6, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', DATEADD(DAY, -1, GETDATE()), 7, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('CLOSED', GETDATE(), 8, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -17, GETDATE()), 1, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -16, GETDATE()), 2, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -15, GETDATE()), 3, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -14, GETDATE()), 4, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -13, GETDATE()), 5, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -12, GETDATE()), 6, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -11, GETDATE()), 7, 1)

INSERT INTO pull_request_event(pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('OPENED', DATEADD(DAY, -10, GETDATE()), 8, 1)


INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -8, GETDATE()), 1, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -7, GETDATE()), 2, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -6, GETDATE()), 3, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -5, GETDATE()), 4, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -4, GETDATE()), 5, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -3, GETDATE()), 6, 2, 'idontknow');

INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -2, GETDATE()), 7, 2, 'idontknow');


INSERT INTO release(remote_identifier, pre_release, [name], author_id, tag_id, repository_id, published_at, draft)
VALUES (1342431, 0, 'first_release', 1, 1, 1, GETDATE(), 0)


INSERT INTO pull_request_comment(event_time, pull_request_id, git_user_id, comment_type)
VALUES (DATEADD(DAY, -1, GETDATE()), 8, 2, 'idontknow');

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASD124A23', 1, 'ASDFASDF', DATEADD(DAY, -10, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASasfgA23', 1, 'ASDFASDF', DATEADD(DAY, -10, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASasWQA23', 1, 'ASDFASDF', DATEADD(DAY, -9, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASD5Wga23', 1, 'ASDFASDF', DATEADD(DAY, -9, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASDF6QA23', 1, 'ASDFASDF', DATEADD(DAY, -8, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('AS3FWQbas', 1, 'ASDFASDF', DATEADD(DAY, -8, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time)
VALUES ('sSDFW2A23', 1, 'ASDFASDF', DATEADD(DAY, -7, GETDATE()));

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASDF4QA23', 1, 'ASDFASDF', DATEADD(DAY, -7, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time, release_id)
VALUES ('ASD5WQA23', 1, 'ASDFASDF', DATEADD(DAY, -6, GETDATE()), 1);

INSERT INTO commits(sha, author_id, message, commit_time)
VALUES ('ASDF26A23', 1, 'ASDFASDF', DATEADD(DAY, -6, GETDATE()));

INSERT INTO commits(sha, author_id, message, commit_time)
VALUES ('AS234QA23', 1, 'ASDFASDF', DATEADD(DAY, -5, GETDATE()));

INSERT INTO commits(sha, author_id, message, commit_time)
VALUES ('ASg2WQA23', 1, 'ASDFASDF', DATEADD(DAY, -5, GETDATE()));

INSERT INTO commits(sha, author_id, message, commit_time)
VALUES ('ASD23sA23', 1, 'ASDFASDF', DATEADD(DAY, -4, GETDATE()));


INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (1, 1, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (3, 2, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (1, 3, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (1, 4, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (3, 5, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (4, 6, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (6, 7, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (4, 8, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (4, 9, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (6, 10, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (6, 11, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (7, 12, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (7, 13, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (2, 1, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (2, 3, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (2, 4, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 1, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 2, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 3, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 4, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 5, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 6, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 8, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (5, 9, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 1, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 2, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 3, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 4, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 5, 1);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 6, 0);

INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 8, 0);


INSERT INTO issue(repository_id, open_user_id, [state], remote_identifier, issue_label_id)
VALUES (1, 1, 'OPEN', 1456432, 1);


INSERT INTO issue_label([label], release_id, remote_identifier)
VALUES ('deploy_bug', 1, 153453);


INSERT INTO deployment_workflow(name, remote_identifier, path, issue_label_id)
VALUES ('deploy_workflow[deployment]', 15343243, '.github/workflow/deployment.yml', 1)


INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 9, 0);


INSERT INTO workflow_run(remote_identifier, deployment_workflow_id, run_number, trigger_event, pull_request_id, conclusion, repository_id, workflow_end_time)
VALUES (2348934, 1, 32, 'PUSH', 8, 'SUCCESS',1, GETDATE())


INSERT INTO release_event(release_event_type, release_id)
VALUES ('PUBLISH', 1)


INSERT INTO issue_event(issue_event_type, event_time, event_sender_id, issue_id)
VALUES ('OPEN', GETDATE(), 1, 1);


INSERT INTO issue_event(issue_event_type, event_time, event_sender_id, issue_id)
VALUES ('CLOSED', DATEADD(day, 2, GETDATE()), 1, 1);

