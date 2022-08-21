INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok1', 1);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok2', 2);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok3', 3);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok4', 4);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok5', 5);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok6', 6);

INSERT INTO git_user (name, git_user_id)
VALUES ('inhyeok7', 7);


INSERT INTO git_organization (name, git_organization_id)
VALUES ('org1', 1);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('org2', 2);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('org3', 3);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('org4', 4);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('org5', 5);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('org6', 6);

INSERT INTO git_organization (name, git_organization_id)
VALUES ('Ring-tail-lemur', 107110653);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (1, 1);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (2, 1);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (3, 2);

INSERT INTO user_organization_table (member_id, git_organization_id)
VALUES (4, 2);


INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo1', 'ORGANIZATION', NULL, 1, 1);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo2', 'ORGANIZATION', NULL, 1, 2);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo3', 'ORGANIZATION', NULL, 1, 3);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo4', 'ORGANIZATION', NULL, 2, 4);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo5', 'ORGANIZATION', NULL, 2, 5);

INSERT INTO repository ([name], owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo6', 'ORGANIZATION', NULL, 2, 6);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo7', 'USER', 1, NULL, 7);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo8', 'USER', 2, NULL, 8);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('repo10', 'USER', 3, NULL, 9);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('RainMaker', 'ORGANIZATION', 4, NULL, 10);

INSERT INTO repository (name, owner_type, owner_user_id, owner_organization_id, repository_id)
VALUES ('test-for-fake-project', 'ORGANIZATION', 5, NULL, 11);


INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a90','branch1', 1, 2);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a91','branch2', 1, 1);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a92','branch3', 1, 2);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a93','branch4', 1, 4);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a94','branch5', 1, 3);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a95','branch6', 1, 3);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a96','branch7', 1, 4);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a97','main', 10, 1);

INSERT INTO branch (branch_id, name, repository_id, git_user_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a98','main', 11, 1);


INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (1, 1, 1, 1, 2);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (2, 2, 1, 2, 3);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (3, 3, 1, 1, 3);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (4, 4, 1, 2, 3);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (5, 5, 1, 3, 4);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (6, 6, 1, 2, 6);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (7, 7, 1, 1, 5);

INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_id,
                          pull_request_close_branch_id)
VALUES (8, 8, 1, 5, 8);


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


INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a90', 'CLOSED', DATEADD(DAY, -7, GETDATE()), 1, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a91', 'CLOSED', DATEADD(DAY, -6, GETDATE()), 2, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a92', 'CLOSED', DATEADD(DAY, -5, GETDATE()), 3, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a93', 'CLOSED', DATEADD(DAY, -4, GETDATE()), 4, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a94', 'CLOSED', DATEADD(DAY, -3, GETDATE()), 5, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a95', 'CLOSED', DATEADD(DAY, -2, GETDATE()), 6, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a96', 'CLOSED', DATEADD(DAY, -1, GETDATE()), 7, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a97', 'CLOSED', GETDATE(), 8, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a98', 'OPENED', DATEADD(DAY, -17, GETDATE()), 1, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a99', 'OPENED', DATEADD(DAY, -16, GETDATE()), 2, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a00', 'OPENED', DATEADD(DAY, -15, GETDATE()), 3, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a01', 'OPENED', DATEADD(DAY, -14, GETDATE()), 4, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a02', 'OPENED', DATEADD(DAY, -13, GETDATE()), 5, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a03', 'OPENED', DATEADD(DAY, -12, GETDATE()), 6, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a04', 'OPENED', DATEADD(DAY, -11, GETDATE()), 7, 1)

INSERT INTO pull_request_event(pull_request_event_id,pull_request_event_type, event_time, pull_request_id, event_sender_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a05', 'OPENED', DATEADD(DAY, -10, GETDATE()), 8, 1)


INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (1, DATEADD(DAY, -8, GETDATE()), 1, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (2, DATEADD(DAY, -7, GETDATE()), 2, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (3, DATEADD(DAY, -6, GETDATE()), 3, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (4, DATEADD(DAY, -5, GETDATE()), 4, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (5, DATEADD(DAY, -4, GETDATE()), 5, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (6, DATEADD(DAY, -3, GETDATE()), 6, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (7, DATEADD(DAY, -2, GETDATE()), 7, 2, 'idontknow');

INSERT INTO pull_request_comment(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
VALUES (8, DATEADD(DAY, -1, GETDATE()), 8, 2, 'idontknow');


INSERT INTO release(release_id, pre_release, [name], author_id, tag_id, repository_id, published_at, draft)
VALUES (1, 0, 'first_release', 1, 1, 1, GETDATE(), 0)

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('1', 1, 'ASDFASDF', DATEADD(DAY, -10, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('2', 1, 'ASDFASDF', DATEADD(DAY, -10, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('3', 1, 'ASDFASDF', DATEADD(DAY, -9, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('4', 1, 'ASDFASDF', DATEADD(DAY, -9, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('5', 1, 'ASDFASDF', DATEADD(DAY, -8, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('6', 1, 'ASDFASDF', DATEADD(DAY, -8, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time)
VALUES ('7', 1, 'ASDFASDF', DATEADD(DAY, -7, GETDATE()));

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('8', 1, 'ASDFASDF', DATEADD(DAY, -7, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time, release_id)
VALUES ('9', 1, 'ASDFASDF', DATEADD(DAY, -6, GETDATE()), 1);

INSERT INTO commits(commit_id, author_id, message, commit_time)
VALUES ('10', 1, 'ASDFASDF', DATEADD(DAY, -6, GETDATE()));

INSERT INTO commits(commit_id, author_id, message, commit_time)
VALUES ('11', 1, 'ASDFASDF', DATEADD(DAY, -5, GETDATE()));

INSERT INTO commits(commit_id, author_id, message, commit_time)
VALUES ('12', 1, 'ASDFASDF', DATEADD(DAY, -5, GETDATE()));

INSERT INTO commits(commit_id, author_id, message, commit_time)
VALUES ('13', 1, 'ASDFASDF', DATEADD(DAY, -4, GETDATE()));


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


INSERT INTO issue(repository_id, open_user_id, [state], issue_id, issue_label_id)
VALUES (1, 1, 'OPEN', 1, 1);


INSERT INTO issue_label([label], release_id, issue_label_id)
VALUES ('deploy_bug', 1, 1);


INSERT INTO deployment_workflow(name, deployment_workflow_id, path, issue_label_id)
VALUES ('deploy_workflow[deployment]', 1, '.github/workflow/deployment.yml', 1)


INSERT INTO pull_request_commit_table(pull_request_id, commit_id, first_commit)
VALUES (8, 9, 0);


INSERT INTO workflow_run(workflow_run_id, deployment_workflow_id, run_number, trigger_event, pull_request_id, conclusion, repository_id, workflow_end_time)
VALUES (1, 1, 32, 'PUSH', 8, 'SUCCESS',1, GETDATE())


INSERT INTO release_event(release_event_type, release_id, release_event_id)
VALUES ('PUBLISH', 1, '10ff7f90-206c-11ed-982c-c290e2bd1a90')


INSERT INTO issue_event(issue_event_id, issue_event_type, event_time, event_sender_id, issue_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a90', 'OPEN', GETDATE(), 1, 1);


INSERT INTO issue_event(issue_event_id, issue_event_type, event_time, event_sender_id, issue_id)
VALUES ('10ff7f90-206c-11ed-982c-c290e2bd1a91', 'CLOSED', DATEADD(day, 2, GETDATE()), 1, 1);

