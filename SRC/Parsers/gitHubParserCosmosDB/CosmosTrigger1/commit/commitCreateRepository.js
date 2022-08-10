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

    // await dbConnectionPool.close();
}

module.exports.insertCommitByUserId = insertCommitByUserId;