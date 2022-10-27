// const pool = require('../ms-sql/msSQLPool');
const err_log_module = require('../utils/slackLogBot.js');
async function insertPullRequestByRepoIdAndBranchId(dbConnectionPool ,remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name, additions, deletions) {

    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name, additions, deletions);

    const sqlQuery = `
        INSERT INTO pull_request (pull_request_id, pull_request_number, repository_id, pull_request_open_branch_name, pull_request_close_branch_name, additions, deletions)
        VALUES ('${remote_identifier}', ${pull_request_number}, ${repository_id}, '${open_branch_name}', '${close_branch_name}', '${additions}', '${deletions}');
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "pullRequestCreateRepository.js // insertPullRequestByRepoIdAndBranchId");
        console.error(e);
    }
}

async function insertPullRequestEventClosedByPullRequestIdAndUserId(dbConnectionPool, context, pull_request_event_id, event_type, event_time, pull_request_id, event_sender_id) {
    // const dbConnectionPool = await pool;
    context.log("****************** flag 3 *******************", );
    context.log(event_type, event_time, pull_request_id, event_sender_id);

    const sqlQuery = `
    INSERT INTO pull_request_event (pull_request_event_type, event_time, pull_request_id, event_sender_id)
    VALUES (UPPER('${event_type}'), '${event_time}', ${pull_request_id}, ${event_sender_id});
    `;
    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "pullRequestCreateRepository.js // insertPullRequestEventClosedByPullRequestIdAndUserId");
        context.log(e);
    }
}


async function insertPullRequestEventOpenByPullRequestIdAndUserId(dbConnectionPool, pull_request_event_id, event_type, event_time, pull_request_id, event_sender_id) {
    // const dbConnectionPool = await pool;
    console.log(event_type, event_time, pull_request_id, event_sender_id);

    const sqlQuery = `
    INSERT INTO pull_request_event (pull_request_event_type, event_time, pull_request_id, event_sender_id)
    VALUES (UPPER('${event_type}'), '${event_time}',  ${pull_request_id}, ${event_sender_id});
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "pullRequestCreateRepository.js // insertPullRequestEventOpenByPullRequestIdAndUserId");
        console.error(e);
    }
}

async function insertPullRequestDirectionBySourcePullRequestId(dbConnectionPool, pull_request_remote_identifier) {
    // const dbConnectionPool = await pool;

    console.log(pull_request_remote_identifier);

    const sqlQuery = `
    WITH
    source_commit_id AS (
        SELECT commit_id
        FROM pull_request_commit_table
        WHERE pull_request_id = ${pull_request_remote_identifier}
    ),
    source_pull_request_id AS (
        SELECT DISTINCT pull_request_id AS source_pull_request_id
        FROM pull_request_commit_table
        WHERE commit_id IN (SELECT * FROM source_commit_id)
        AND pull_request_id NOT IN (
            SELECT source_pull_request_id
            FROM pull_request_direction
            )
        AND pull_request_id != ${pull_request_remote_identifier}
    )
    INSERT INTO pull_request_direction (source_pull_request_id, outgoing_pull_request_id)
    SELECT *
    FROM source_pull_request_id
    CROSS JOIN (
    SELECT ${pull_request_remote_identifier} AS outgoing_pull_request_id
    ) AS outgoing_pull_request_id;
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "pullRequestCreateRepository.js // insertPullRequestDirectionBySourcePullRequestId");
        console.error(e);
    }
}

module.exports.insertPullRequestByRepoIdAndBranchId = insertPullRequestByRepoIdAndBranchId;
module.exports.insertPullRequestEventOpenByPullRequestIdAndUserId = insertPullRequestEventOpenByPullRequestIdAndUserId;
module.exports.insertPullRequestEventClosedByPullRequestIdAndUserId = insertPullRequestEventClosedByPullRequestIdAndUserId;
module.exports.insertPullRequestDirectionBySourcePullRequestId = insertPullRequestDirectionBySourcePullRequestId;
