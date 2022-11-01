BEGIN TRAN
DECLARE @repositoryId BIGINT
SET @repositoryId = 544985444



DECLARE @organizationId BIGINT
SET @organizationId = (SELECT owner_organization_id
                       FROM repository
                       WHERE repository_id = @repositoryId)
-- Repo 정보 삭제
DELETE
FROM repository
OUTPUT deleted.*
WHERE repository_id = @repositoryId

DELETE
FROM oauth_user_repository_table
OUTPUT deleted.*
WHERE repository_id = @repositoryId


-- Organization 정보 삭제
DELETE
FROM git_organization
OUTPUT deleted.*
WHERE git_organization_id = @organizationId

-- Branch 정보 삭제
DELETE
FROM branch
OUTPUT deleted.*
WHERE repository_id = @repositoryId;

-- Pull_request, Pull_request_commit_table, Commits 정보 삭제
DECLARE @PR_id TABLE
               (
                   pull_request_id BIGINT
               )

INSERT INTO @PR_id
SELECT pull_request_id
FROM pull_request
WHERE repository_id = @repositoryId;

DELETE PR
OUTPUT deleted.*
FROM pull_request PR
         JOIN pull_request_commit_table PRCT
              ON PR.pull_request_id = PRCT.pull_request_id
         JOIN commits C
              ON PRCT.commit_id = C.commit_id
WHERE PR.repository_id = @repositoryId;

DELETE
FROM pull_request_comment
OUTPUT deleted.*
WHERE pull_request_id IN (SELECT pull_request_id FROM @PR_id);

DELETE
FROM pull_request_event
OUTPUT deleted.*
WHERE pull_request_id IN (SELECT pull_request_id FROM @PR_id);

DELETE
FROM pull_request_direction
OUTPUT deleted.*
WHERE source_pull_request_id IN (SELECT pull_request_id FROM @PR_id);

-- release 관련 정보 삭제
DELETE
FROM release
OUTPUT deleted.*
WHERE release.repository_id = @repositoryId

DELETE
FROM release_success
OUTPUT deleted.*
WHERE release_success.repository_id = @repositoryId

-- lead_time_for_change 정보 삭제
DELETE
FROM lead_time_for_change
OUTPUT deleted.*
WHERE lead_time_for_change.repository_id = @repositoryId

ROLLBACK TRAN