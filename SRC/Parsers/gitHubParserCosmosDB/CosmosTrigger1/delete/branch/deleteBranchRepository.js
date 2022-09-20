async function updateBranchByRepoIdAndUserId(dbConnectionPool, branch_id, branch_name, repository_id, author_id){


    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id)
    VALUES ('${branch_name}', ${repository_id}, ${author_id});
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);


}

module.exports.updateBranchByRepoIdAndUserId = updateBranchByRepoIdAndUserId;