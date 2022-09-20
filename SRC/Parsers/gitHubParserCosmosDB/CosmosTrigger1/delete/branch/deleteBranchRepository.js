async function updateBranchByRepoIdAndUserId(dbConnectionPool, branch_name, repository_id, author_id){


    const sqlQuery = `
    DECLARE @branch_id BIGINT
    SET @branch_id = (
           SELECT TOP 1 branch_id
           FROM branch
           WHERE repository_id = ${repository_id}
           AND name = '${branch_name}'
           AND is_closed = 'false'
           AND create_type = 'BRANCH'
       )

    UPDATE branch
    SET is_closed = 'true'
    WHERE branch_id = @branch_id;
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);


}

module.exports.updateBranchByRepoIdAndUserId = updateBranchByRepoIdAndUserId;