async function insertIssueLabel(dbConnectionPool, releaseId, labelId, labelName, repositoryId, context){

    const sqlQuery = `
    INSERT INTO issue_label([label], release_id, issue_label_id, repository_id)
    VALUES ('${labelName}', ${releaseId}, ${labelId}, ${repositoryId})
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

async function insertIssueLabelByApi(findLabel) {
    console.log(findLabel);
}

module.exports.insertIssueLabel = insertIssueLabel;
