const pool = require('../ms-sql/msSQLPool');

async function repositoryCreateMain(eventObject, context){
    //repository entity 생성 및 삽입

    const dbConnectionPool = await pool;

    if(eventObject.repository_owner_type == "Organization") {
        
        const sqlQuery = `
        INSERT INTO repository (name, owner_type, owner_organization_id)
        VALUES ('${eventObject.repository_name}', 'ORGANIZATION', 
        (
            SELECT git_organization_id 
            FROM git_organization 
            WHERE remote_identifier = ${eventObject.repository_owner_id}
        ))
        `;
        console.log(sqlQuery);
        
        await dbConnectionPool.request()
            .query(sqlQuery);
        
    } else if (eventObject.repository_owner_type == "User") {
        
        const sqlQuery = `
        INSERT INTO repository (name, owner_type, owner_user_id)
        VALUES ('${eventObject.repository_name}', 'USER',
        (
            SELECT git_user_id
            FROM git_user
            WHERE remote_identifier = ${eventObject.repository_owner_id}
        ))
        `;
        console.log(sqlQuery);

        await dbConnectionPool.request()
            .query(sqlQuery);
    }
    // console.log("eventObject : \n", eventObject);

    await dbConnectionPool.close();
}
module.exports.repositoryCreateMain = repositoryCreateMain;

/*
레포지토리 생성 이벤트
{
    "hook_event":"repository",
    "source":"github",
    "action":"created",
    "repository_remote_id":"520479928",
    "repository_name":"dfsasdfsf",
    "repository_owner_type":"Organization",
    "repository_owner_id":"107110653"
}

/*
레포지토리 생성 이벤트
{
    "hook_event":"repository",
    "source":"github",
    "action":"created",
    "repository_remote_id":"520479928",
    "repository_name":"dfsasdfsf",
    "repository_owner_type":"Organization",
    "repository_owner_id":"107110653"
}

*/