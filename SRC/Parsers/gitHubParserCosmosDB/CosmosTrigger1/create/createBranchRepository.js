const pool = require('../ms-sql/msSQLPool');

async function insertBranchByRepoRemoteIdAndUserId(branch_name, repository_remote_id, author_id){

    const dbConnectionPool = await pool;




    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id)
    VALUES ( '${branch_name}', 
    (
        SELECT repository_id
        FROM repository
        WHERE remote_identifier = ${repository_remote_id}
    ),
    (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${author_id}
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


}

module.exports.insertBranchByRepoIdAndUserId = insertBranchByRepoIdAndUserId;
module.exports.insertBranchByRepoRemoteIdAndUserId = insertBranchByRepoRemoteIdAndUserId;