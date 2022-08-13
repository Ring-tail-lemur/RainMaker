const pool = require('../ms-sql/msSQLPool');

async function insertPullRequestByRepoIdAndBranchId(dbConnectionPool ,remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name) {

    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);
    // const dbConnectionPool = await pool;
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

async function insertPullRequestEventClosedByPullRequestIdAndUserId(dbConnectionPool, context, event_type, event_time, pull_request_id, event_sender_id) {
    // const dbConnectionPool = await pool;
    context.log("****************** flag 3 *******************", );
    context.log(event_type, event_time, pull_request_id, event_sender_id);

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
    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        context.log(e);
    }
}


async function insertPullRequestEventOpenByPullRequestIdAndUserId(dbConnectionPool ,event_type, event_time, pull_request_id, event_sender_id) {
    // const dbConnectionPool = await pool;
    console.log(event_type, event_time, pull_request_id, event_sender_id);

    const sqlQuery = `
    INSERT INTO pull_request_event (event_type, event_time, pull_request_id, event_sender_id)
    VALUES (UPPER('${event_type}'), '${event_time}',  ${pull_request_id},
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

async function insertPullRequestDirectionBySourcePullRequestId(dbConnectionPool, pull_request_remote_identifier) {
    // const dbConnectionPool = await pool;

    console.log(pull_request_remote_identifier);

    const sqlQuery = `
    WITH out_going_pull_request_id AS (
        SELECT TOP 1 pull_request_id AS out_going_pull_request_id
        FROM pull_request
        WHERE remote_identifier = ${pull_request_remote_identifier}
    ),
    source_commit_id AS (
        SELECT commit_id
        FROM pull_request_commit_table
        WHERE pull_request_id = (SELECT out_going_pull_request_id FROM out_going_pull_request_id)
    ),
    source_pull_request_id AS (
        SELECT DISTINCT pull_request_id AS source_pull_request_id
        FROM pull_request_commit_table
        WHERE commit_id IN (SELECT * FROM source_commit_id)
        AND pull_request_id NOT IN (
            SELECT source_pull_request_id
            FROM pull_request_direction
            )
        AND pull_request_id != (SELECT out_going_pull_request_id FROM out_going_pull_request_id)
    )
    INSERT INTO pull_request_direction (source_pull_request_id, outgoing_pull_request_id)
    SELECT *
    FROM source_pull_request_id
    CROSS JOIN out_going_pull_request_id
    ;
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }
}

module.exports.insertPullRequestByRepoIdAndBranchId = insertPullRequestByRepoIdAndBranchId;
module.exports.insertPullRequestEventOpenByPullRequestIdAndUserId = insertPullRequestEventOpenByPullRequestIdAndUserId;
module.exports.insertPullRequestEventClosedByPullRequestIdAndUserId = insertPullRequestEventClosedByPullRequestIdAndUserId;
module.exports.insertPullRequestDirectionBySourcePullRequestId = insertPullRequestDirectionBySourcePullRequestId;
