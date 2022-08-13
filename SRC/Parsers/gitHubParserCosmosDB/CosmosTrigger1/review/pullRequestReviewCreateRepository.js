const pool = require('../ms-sql/msSQLPool');

async function insertPullRequestCommentByPullRequestIdAndUserId(dbConnectionPool, context, event_time, pull_request_id, git_user_id, comment_type) {
    // const dbConnectionPool = await pool;
    context.log("****************** flag 2 *******************", );
    context.log(event_time, pull_request_id, git_user_id, comment_type);

    const sqlQuery = `
    INSERT INTO pull_request_comment (event_time, pull_request_id, git_user_id, comment_type)
    VALUES ('${event_time}',
        (
        SELECT pull_request_id
        FROM pull_request
        WHERE remote_identifier = ${pull_request_id}
         ),
        (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${git_user_id}
         ),
       '${comment_type}'
     )
    `;
    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        context.error(e);
    }
}

module.exports.insertPullRequestCommentByPullRequestIdAndUserId = insertPullRequestCommentByPullRequestIdAndUserId;