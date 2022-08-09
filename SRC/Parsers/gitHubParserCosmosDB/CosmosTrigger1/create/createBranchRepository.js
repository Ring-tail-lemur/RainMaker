const pool = require('../ms-sql/msSQLPool');

async function insertBranchByRepoIdAndUserId(branch_name, repository_id, author_id){

    const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id)
    VALUES ( '${branch_name}', ${repository_id},
    (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${author_id}
    ));
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

    await dbConnectionPool.close();
}

module.exports.insertBranchByRepoIdAndUserId = insertBranchByRepoIdAndUserId;