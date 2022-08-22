const pool = require('../ms-sql/msSQLPool');

async function insertCommitByUserId(dbConnectionPool, commit_sha, author_id, message, commit_time) {

    // const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO commits (commit_id, message, commit_time, author_id)
    VALUES ('${commit_sha}', '${message}', '${commit_time}', ${author_id});
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

async function insertPullRequestCommitTableByPullRequestIdAndCommitId(dbConnectionPool, pull_request_id, commit_id, first_commit) {

    console.log(pull_request_id, commit_id, first_commit);

    // const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO pull_request_commit_table (pull_request_id, commit_id, first_commit)
    VALUES ( ${pull_request_id}, '${commit_id}', '${first_commit}');`;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}
module.exports.insertCommitByUserId = insertCommitByUserId;
module.exports.insertPullRequestCommitTableByPullRequestIdAndCommitId = insertPullRequestCommitTableByPullRequestIdAndCommitId;