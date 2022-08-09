const pool = require('../ms-sql/msSQLPool');

async function insertPullRequestByRepoIdAndBranchId(remote_identifier, pull_request_number,process_end, repository_id, open_branch_id, close_branch_id) {

    console.log(remote_identifier, pull_request_number,process_end, repository_id, open_branch_id, close_branch_id);

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

module.exports.insertPullRequestByRepoIdAndBranchId = insertPullRequestByRepoIdAndBranchId;