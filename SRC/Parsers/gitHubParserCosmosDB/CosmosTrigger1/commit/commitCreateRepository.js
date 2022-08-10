const pool = require('../ms-sql/msSQLPool');

async function insertCommitByUserId(commit_sha, author_id, message, commit_time) {

    const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO commits (sha, message, commit_time, author_id)
    VALUES ('${commit_sha}', '${message}', '${commit_time}',
            (
                SELECT git_user_id
                FROM git_user
                WHERE git_user_id = ${author_id}
            ));
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

async function insertPullRequestCommitTableByPullRequestIdAndCommitId(pull_request_id, commit_id, first_commit) {

    console.log(pull_request_id, commit_id, first_commit);

    const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO pull_request_commit_table (pull_request_id, commit_id, first_commit)
    VALUES (
        (
        SELECT pull_request.pull_request_id
        FROM pull_request
        WHERE remote_identifier = ${pull_request_id}
        ),
        (
        SELECT commits.commit_id
        FROM commits
        WHERE sha = '${commit_id}'
        ),
        '${first_commit}'
    );
    `;
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