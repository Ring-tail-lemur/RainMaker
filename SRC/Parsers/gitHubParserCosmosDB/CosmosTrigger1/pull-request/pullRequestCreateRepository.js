const pool = require('../ms-sql/msSQLPool');

async function insertPullRequestByRepoIdAndBranchId(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name) {

    const dbConnectionPool = await pool;
    console.log(remote_identifier, pull_request_number, repository_id, open_branch_name, close_branch_name);

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

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.error(e);
    }


}

module.exports.insertPullRequestByRepoIdAndBranchId = insertPullRequestByRepoIdAndBranchId;