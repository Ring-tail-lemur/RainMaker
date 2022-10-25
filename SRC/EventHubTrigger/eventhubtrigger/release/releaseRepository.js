const err_log_module = require('../utils/slackLogBot.js');
async function insertReleaseByUserIdAndTagIdAndRepoId(dbConnectionPool, context, release_event_id, user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type) {


    console.log(user_id, tag_name, repo_id, remote_identifier, pre_release, name, published_at, draft, release_event_type);

    const sqlQuery = `
    INSERT INTO release_event (release_event_id, release_event_type, release_id)
    VALUES ('${release_event_id}', UPPER('${release_event_type}'), ${remote_identifier} )
    INSERT INTO release (release_id, pre_release, name, author_id, tag_name, repository_id, published_at, draft)
    VALUES (${remote_identifier}, '${pre_release}', '${name}', ${user_id}, '${tag_name}', ${repo_id}, '${published_at}', '${draft}')
    `

    console.log(sqlQuery);
    let result;
    try {
        result = await dbConnectionPool.request()
             .query(sqlQuery);
    } catch (e) {
        context.log("===Error FROM INSERT RELEASE===");
        err_log_module.log(e, "releaseRepository.js // insertReleaseByUserIdAndTagIdAndRepoId");
        context.log(e);
    }
    return result;
}

async function deleteReleaseByReleaseId(dbConnectionPool, context, release_event_id) {

    const sqlQuery = `
    DELETE
    FROM release
    WHERE release_id = ${release_event_id};
    `

    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        context.log("===Error FROM INSERT DELETE RELEASE===");
        err_log_module.log(e, "releaseRepository.js // deleteReleaseByReleaseId");
        context.log(e);
    }

}


/**
 * repo_id를 넣어주면 가장 최근의 release 이름을 알려주는 함수
 * @return null/releaseName
 * */
async function selectReleaseByMaxPublishedAt(dbConnectionPool, repo_id) {
    const sqlQuery = `
    SELECT *
    FROM release
    WHERE published_at = (SELECT MAX(published_at)
                          FROM release
                          WHERE repository_id = ${repo_id}
                          GROUP BY repository_id)
    `
    let max_release_name;
    try {
        max_release_name = await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "releaseRepository.js // selectReleaseByMaxPublishedAt");
        console.error(e);
    }
}

module.exports.insertReleaseByUserIdAndTagIdAndRepoId = insertReleaseByUserIdAndTagIdAndRepoId;
module.exports.deleteReleaseByReleaseId = deleteReleaseByReleaseId;