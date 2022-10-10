// const pool = require('../../ms-sql/msSQLPool');
const errLogModule = require('../../utils/slackLogBot.js');

async function insertBranchByRepoRemoteIdAndUserId(dbConnectionPool, branch_id, branch_name, repository_remote_id, author_id, context){

    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id)
    VALUES ('${branch_name}', ${repository_remote_id}, ${author_id})
    `;
    console.log(sqlQuery);
    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        errLogModule.log(e, "createBranchRepository.js // insertBranchByRepoRemoteIdAndUserId");
        console.error(e);
    }

}


async function insertBranchByRepoIdAndUserId(dbConnectionPool, branch_id, branch_name, repository_id, author_id){

    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id)
    VALUES ('${branch_name}', ${repository_id}, ${author_id});
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);


}

module.exports.insertBranchByRepoIdAndUserId = insertBranchByRepoIdAndUserId;
module.exports.insertBranchByRepoRemoteIdAndUserId = insertBranchByRepoRemoteIdAndUserId;