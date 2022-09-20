// const pool = require('../ms-sql/msSQLPool');
// const sql = require('mssql');

async function insertRepoByUserId(dbConnectionPool, repository_name, repository_remote_id, repository_owner_id){

    const sqlQuery = `
    INSERT INTO repository (name, owner_type, repository_id, owner_organization_id)
    VALUES ('${repository_name}', 'USER', ${repository_remote_id}, ${repository_owner_id} )
    `;
    console.log(sqlQuery);

    await dbConnectionPool.request()
        .query(sqlQuery);

}


async function insertRepoByOrganizationId(dbConnectionPool, repository_name, repository_remote_id, repository_owner_id){

    const sqlQuery = `
    INSERT INTO repository (name, owner_type, repository_id, owner_organization_id)
    VALUES ('${repository_name}', 'ORGANIZATION', ${repository_remote_id}, ${repository_owner_id})
    `;
    console.log(sqlQuery);
    
    await dbConnectionPool.request()
        .query(sqlQuery);

}

module.exports.insertRepoByUserId = insertRepoByUserId;
module.exports.insertRepoByOrganizationId = insertRepoByOrganizationId;