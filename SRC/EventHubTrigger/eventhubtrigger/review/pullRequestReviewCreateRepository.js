// const pool = require('../ms-sql/msSQLPool');
const err_log_module = require('../utils/slackLogBot.js');
async function insertPullRequestCommentByPullRequestIdAndUserId(dbConnectionPool, context, pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type,) {
    // const dbConnectionPool = await pool;
    console.log(pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type);

    const sqlQuery = `
    INSERT INTO pull_request_comment (pull_request_comment_id, event_time, pull_request_id, git_user_id, comment_type)
    VALUES (${pull_request_comment_id}, '${event_time}', ${pull_request_id}, ${git_user_id}, 'REVIEW'
     )
    `;
    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "pullRequestReviewCreateRepository.js");
        context.error(e);
    }
}

module.exports.insertPullRequestCommentByPullRequestIdAndUserId = insertPullRequestCommentByPullRequestIdAndUserId;