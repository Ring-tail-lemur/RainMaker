async function insertReleaseByUserIdAndTagIdAndRepoId(dbConnectionPool, user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type) {

    console.log(user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type);

    const sqlQuery = `
        DECLARE @releaseId INT
        DECLARE @authorId INT
        DECLARE @repositoryId INT
        DECLARE @tagId INT
        -- 태그 ID 구현하지 않음.
        SET @authorId =
                (
                SELECT TOP 1 git_user_id
                FROM git_user
                WHERE remote_identifier = ${user_id}
                )
        SET @repositoryId =
                (
                SELECT TOP 1 repository_id
                FROM repository
                WHERE remote_identifier = ${repo_id}
                )
        SET @releaseId =
                (
                 SELECT TOP 1 release_id
                 FROM release
                 WHERE remote_identifier = ${remote_identifier}
                )
        SET @tagId =
                (
                SELECT TOP 1 branch_id
                FROM branch
                WHERE name = '${tag_name}'
                AND create_type = 'TAG'
                )
        IF
        ((
            @releaseId
        ) IS NOT NULL)
        BEGIN
            PRINT('releaseID')
            PRINT(@releaseId)
            INSERT INTO release_event (release_event_type, release_id)
            VALUES ('${release_event_type}', @releaseId)
        END
        ELSE
        BEGIN
            PRINT('NOT IN')
            INSERT INTO release (remote_identifier, pre_release, name, author_id, tag_id, repository_id, published_at, draft)
            VALUES (${remote_identifier}, '${pre_release}', '${name}', @authorId, @tagId, @repositoryId, '${published_at}', '${draft}')
            INSERT INTO release_event (release_event_type, release_id)
            VALUES ('${release_event_type}', (SELECT @@IDENTITY AS SEQ) )
        END
    `

    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
             .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

module.exports.insertReleaseByUserIdAndTagIdAndRepoId = insertReleaseByUserIdAndTagIdAndRepoId;