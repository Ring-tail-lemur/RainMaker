async function insertReleaseByUserIdAndTagIdAndRepoId(dbConnectionPool, user_id, tag_id, repo_id) {

    console.log(user_id, tag_id, repo_id);

    const sqlQuery = `
    
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