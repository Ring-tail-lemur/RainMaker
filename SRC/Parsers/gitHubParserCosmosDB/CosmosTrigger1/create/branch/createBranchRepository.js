const pool = require('../../ms-sql/msSQLPool');

async function insertBranchByRepoRemoteIdAndUserId(dbConnectionPool, branch_id, branch_name, repository_remote_id, author_id, context){

    const sqlQuery = `
    INSERT INTO branch (branch_id, name, repository_id, git_user_id)
    VALUES ('${branch_id}','${branch_name}', ${repository_remote_id}, ${author_id})
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


async function insertBranchByRepoIdAndUserId(dbConnectionPool, branch_id, branch_name, repository_id, author_id){

    // const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO branch (branch_id, name, repository_id, git_user_id)
    VALUES ('${branch_id}', '${branch_name}', ${repository_id}, ${author_id});
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);


}

module.exports.insertBranchByRepoIdAndUserId = insertBranchByRepoIdAndUserId;
module.exports.insertBranchByRepoRemoteIdAndUserId = insertBranchByRepoRemoteIdAndUserId;