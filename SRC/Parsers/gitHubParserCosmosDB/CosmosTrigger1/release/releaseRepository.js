async function insertReleaseByUserIdAndTagIdAndRepoId(dbConnectionPool, release_event_id, user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type) {

    console.log(user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type);
    // todo 나중에 병령처리 하려면 로직을 살짝 바꿔와야할듯.
    const sqlQuery = `
    INSERT INTO release_event (release_event_id, release_event_type, release_id)
    VALUES ('${release_event_id}', '${release_event_type}', ${remote_identifier} )
    INSERT INTO release (release_id, pre_release, name, author_id, tag_name, repository_id, published_at, draft)
    VALUES (${remote_identifier}, '${pre_release}', '${name}', ${user_id}, '${tag_name}', ${repo_id}, '${published_at}', '${draft}')
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