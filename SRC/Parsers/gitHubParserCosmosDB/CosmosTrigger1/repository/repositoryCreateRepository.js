const pool = require('../ms-sql/msSQLPool');
const sql = require('mssql');

async function insertRepoByUserId(repository_name, repository_remote_id, repository_owner_id){

    const dbConnectionPool = await pool;
    
    const sqlQuery = `
    INSERT INTO repository (name, owner_type, remote_identifier, owner_organization_id)
    VALUES ('${repository_name}', 'USER', ${repository_remote_id},
    (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${repository_owner_id}
    ))
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);
    
    dbConnectionPool.close();
}


async function insertRepoByOrganizationId(repository_name, repository_remote_id, repository_owner_id){

    const dbConnectionPool = await pool;

    const transaction = await dbConnectionPool.transaction();

    await transaction.begin();

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
    
    const repoId = await dbConnectionPool.request()
        .query("SELECT @@identity AS id;");
        // 넣은 repository의 Id를 받아옴.

    console.log(repoId);
    await transaction.commit();
    
    // await dbConnectionPool.close();
    return repoId.recordset[0].id;
}

module.exports.insertRepoByUserId = insertRepoByUserId;
module.exports.insertRepoByOrganizationId = insertRepoByOrganizationId;