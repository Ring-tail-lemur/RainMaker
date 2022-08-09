const pool = require('../ms-sql/msSQLPool');

async function insertCommitByUserId(commit_sha, author_id, message) {

    console.log(commit_sha, author_id, message);

    const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO repository (name, owner_type, remote_identifier, owner_organization_id)
    VALUES ('${repository_name}', 'ORGANIZATION', ${repository_remote_id},
    (
        SELECT git_organization_id 
        FROM git_organization 
        WHERE remote_identifier = ${repository_owner_id}
    ))
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}

module.exports.insertCommitByUserId = insertCommitByUserId;