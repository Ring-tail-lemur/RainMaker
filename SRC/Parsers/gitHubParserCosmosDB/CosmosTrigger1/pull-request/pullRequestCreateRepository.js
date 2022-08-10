const pool = require('../ms-sql/msSQLPool');

async function insertPullRequestByRepoIdAndBranchId(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name) {

    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);
    const dbConnectionPool = await pool;
    const transaction = await dbConnectionPool.transaction();
    await transaction.begin();

    const sqlQuery = `
        WITH repo_id AS (
            SELECT
                TOP 1 repository_id
            FROM repository
            WHERE remote_identifier = ${repository_id}
        )

        INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id, pull_request_open_branch_id, pull_request_close_branch_id)
        VALUES (${remote_identifier}, ${pull_request_number}, 
            (
            SELECT repository_id 
            FROM repo_id
            ),
            (
            SELECT branch_id
            FROM branch B
            WHERE B.repository_id = (SELECT repository_id FROM repo_id)
              AND B.name = '${open_branch_name}'
            ),
            (
            SELECT branch_id
            FROM branch B
            WHERE B.repository_id = (SELECT repository_id FROM repo_id)
              AND B.name = '${close_branch_name}'
            ));
    `;
    console.log(sqlQuery);

    let pullRequestId;

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
        pullRequestId = await dbConnectionPool.request()
            .query("SELECT @@identity AS id;");
        await transaction.commit();
    } catch (e) {
        console.error(e);
    }

    console.log(pullRequestId);


    return pullRequestId.recordset[0].id;
}

async function insertPullRequestEventByPullRequestIdAndUserId(event_type, event_time, pull_request_id, event_sender_id) {
    const dbConnectionPool = await pool;
    console.log(event_type, event_time, pull_request_id, event_sender_id);

    const sqlQuery = `
    INSERT INTO pull_request_event (event_type, event_time, pull_request_id, event_sender_id)
    VALUES (UPPER('${event_type}'), '${event_time}', 
        (
        SELECT pull_request_id
        FROM pull_request
        WHERE remote_identifier = ${pull_request_id}
        ),
        (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${event_sender_id}
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

async function insertPullRequestDirectionBySourcePullRequestId(event_type, event_time, pull_request_id, event_sender_id) {
    const dbConnectionPool = await pool;
    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);

    const sqlQuery = ``;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

async function b(event_type, event_time, pull_request_id, event_sender_id) {
    const dbConnectionPool = await pool;
    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);

    const sqlQuery = ``;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

async function c(event_type, event_time, pull_request_id, event_sender_id) {
    const dbConnectionPool = await pool;
    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);

    const sqlQuery = ``;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

module.exports.insertPullRequestByRepoIdAndBranchId = insertPullRequestByRepoIdAndBranchId;
module.exports.insertPullRequestEventByPullRequestIdAndUserId = insertPullRequestEventByPullRequestIdAndUserId;