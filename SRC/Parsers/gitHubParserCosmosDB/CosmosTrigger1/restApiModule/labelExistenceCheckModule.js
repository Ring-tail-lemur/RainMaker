async function labelExistenceCheck(dbConnectionPool, repositoryId, labelId, labelName, context){

    const sqlQuery = `select count(*) from issue_label where repository_id = ${repositoryId} AND issue_label.label = ${labelName}`;
    console.log(sqlQuery);

    return await dbConnectionPool.request()
        .query(sqlQuery);
}

module.exports.labelExistenceCheck = labelExistenceCheck;
