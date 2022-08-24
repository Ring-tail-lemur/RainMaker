async function insertIssueByRepoIdAndLabelId(dbConnectionPool, issue_id, repo_id, user_id, state, issue_label_id, context){

    const sqlQuery = `
    INSERT INTO issue (issue_id, repository_id, open_user_id, state, issue_label_id)
    VALUES (${issue_id}, ${repo_id}, ${user_id}, ${state}, ${issue_label_id});
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

async function deleteIssueByIssueId(dbConnectionPool, labelId, repositoryId, context){

    const sqlQuery = `
    DELETE FROM issue_label
    WHERE issue_label_id = ${labelId}
    AND repository_id = ${repositoryId}
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

module.exports.insertIssueByRepoIdAndLabelId = insertIssueByRepoIdAndLabelId;
module.exports.deleteIssueByIssueId = deleteIssueByIssueId;