async function insertIssueLabel(dbConnectionPool, labelId, labelName, repositoryId, context){

    const sqlQuery = `
    INSERT INTO issue_label([label], issue_label_id, repository_id)
    VALUES ('${labelName}', ${labelId}, ${repositoryId})
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

async function deleteIssueLabel(dbConnectionPool, labelId, repositoryId, context){

    const sqlQuery = `
    DELETE FROM issue_label
    WHERE issue_label_id = ${labelId}
    AND repository_id = ${repositoryId}
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

module.exports.insertIssueLabel = insertIssueLabel;
module.exports.deleteIssueLabel = deleteIssueLabel;