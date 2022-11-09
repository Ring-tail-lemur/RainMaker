async function insertIssueByRepoIdAndLabelId(dbConnectionPool, issue_id, repo_id, user_id, state, issue_label_id, issue_number, context){

    const sqlQuery = `
    INSERT INTO issue (issue_id, repository_id, open_user_id, state, issue_label_id, issue_number)
    VALUES (${issue_id}, ${repo_id}, ${user_id}, UPPER('${state}'), ${issue_label_id}, ${issue_number});
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

async function insertIssueEventByIssueIdAndUserId(dbConnectionPool, issue_event_id, state, event_time, event_sender_id, issue_id,context){

    const sqlQuery = `
    INSERT INTO issue_event (issue_event_id, issue_event_type, event_time, event_sender_id, issue_id)
    VALUES ('${issue_event_id}', UPPER('${state}'), '${event_time}', ${event_sender_id}, ${issue_id})
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

async function updateIssueByIssueIdAndRepoId(dbConnectionPool, issue_id, repo_id, state, issue_label_id, context){

    const sqlQuery = `
    UPDATE issue
    SET state = UPPER('${state}'),
        issue_label_id = ${issue_label_id}
    WHERE issue_id = ${issue_id}
    AND repository_id = ${repo_id}
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}


async function deleteIssueByIssueId(dbConnectionPool, labelId, repositoryId, context){

    const sqlQuery = `
    DELETE FROM issue
    WHERE issue_label_id = ${labelId}
    AND repository_id = ${repositoryId}
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

module.exports.insertIssueByRepoIdAndLabelId = insertIssueByRepoIdAndLabelId;
module.exports.deleteIssueByIssueId = deleteIssueByIssueId;
module.exports.updateIssueByIssueIdAndRepoId = updateIssueByIssueIdAndRepoId;
module.exports.insertIssueEventByIssueIdAndUserId = insertIssueEventByIssueIdAndUserId;