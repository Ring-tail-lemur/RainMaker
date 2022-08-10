CREATE TRIGGER updateCommitsModified
ON dbo.commits
AFTER UPDATE
AS
   UPDATE dbo.commits
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.commits.commit_id = i.commit_id;
go

CREATE TRIGGER updateBranchModified
ON dbo.branch
AFTER UPDATE
AS
   UPDATE dbo.branch
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.branch.branch_id = i.branch_id;
go

CREATE TRIGGER updateDeploymentEventModified
ON dbo.deployment_event
AFTER UPDATE
AS
   UPDATE dbo.deployment_event
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.deployment_event.deployment_event_id = i.deployment_event_id;
go

CREATE TRIGGER updateGitOrganizationModified
ON dbo.git_organization
AFTER UPDATE
AS
   UPDATE dbo.git_organization
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.git_organization.git_organization_id = i.git_organization_id;
go

CREATE TRIGGER updateGitUserModified
ON dbo.git_user
AFTER UPDATE
AS
   UPDATE dbo.git_user
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.git_user.git_user_id = i.git_user_id;
go

CREATE TRIGGER updateLeadTimeForChangeModified
ON dbo.lead_time_for_change
AFTER UPDATE
AS
   UPDATE dbo.lead_time_for_change
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.lead_time_for_change.lead_time_for_change_id = i.lead_time_for_change_id;
go

CREATE TRIGGER updatePullRequestModified
ON dbo.pull_request
AFTER UPDATE
AS
   UPDATE dbo.pull_request
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.pull_request.pull_request_id = i.pull_request_id;
go

CREATE TRIGGER updatePullRequestCommentModified
ON dbo.pull_request_comment
AFTER UPDATE
AS
   UPDATE dbo.pull_request_comment
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.pull_request_comment.pull_request_comment_id = i.pull_request_comment_id;
go

CREATE TRIGGER updatePullRequestCommitTableModified
ON dbo.pull_request_commit_table
AFTER UPDATE
AS
   UPDATE dbo.pull_request_commit_table
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.pull_request_commit_table.pull_request_commit_table_id = i.pull_request_commit_table_id;
go

CREATE TRIGGER updatePullRequestEventModified
ON dbo.pull_request_event
AFTER UPDATE
AS
   UPDATE dbo.pull_request_event
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.pull_request_event.pull_request_event_id = i.pull_request_event_id;
go

CREATE TRIGGER updateRepositoryModified
ON dbo.repository
AFTER UPDATE
AS
   UPDATE dbo.repository
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.repository.repository_id = i.repository_id;
go

CREATE TRIGGER updateUserOrganizationTableModified
ON dbo.user_organization_table
AFTER UPDATE
AS
   UPDATE dbo.user_organization_table
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.user_organization_table.user_organization_table_id = i.user_organization_table_id;
go

CREATE TRIGGER updateIssueModified
ON dbo.issue
AFTER UPDATE
AS
   UPDATE dbo.issue
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.issue.issue_id = i.issue_id;
go

CREATE TRIGGER updateIssueEventModified
ON dbo.issue_event
AFTER UPDATE
AS
   UPDATE dbo.issue_event
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.issue_event.issue_event_id = i.issue_event_id;
go

CREATE TRIGGER updateIssue_labelModified
ON dbo.issue_label
AFTER UPDATE
AS
   UPDATE dbo.issue_label
   SET modified_date = SYSDATETIME()
   FROM Inserted i
   WHERE dbo.issue_label.issue_label_id = i.issue_label_id;
go
