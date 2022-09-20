async function updateBranchByRepoIdAndUserId(dbConnectionPool, context, branch_name, repository_id, author_id){


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
    context.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        context.error(e);
    }


}

module.exports.updateBranchByRepoIdAndUserId = updateBranchByRepoIdAndUserId;