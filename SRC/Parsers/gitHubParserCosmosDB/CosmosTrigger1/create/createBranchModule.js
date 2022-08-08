const pool = require('../ms-sql/msSQLPool');

async function createBranchMain(eventObject, context) {
    // branch entity 생성 및 삽입

    const dbConnectionPool = await pool;

    const sqlQuery = `
        INSERT INTO branch (name, repository_id, git_user_id)
        VALUES ( '${eventObject.branch_name}', 
        (
            SELECT repository_id
            FROM repository
            WHERE remote_identifier = ${eventObject.repository_id}
        ),
        (
            SELECT git_user_id
            FROM git_user
            WHERE remote_identifier = ${eventObject.author_id}
        ));
        `;
    
    console.log(sqlQuery);
    
    try{
    await dbConnectionPool.request()
        .query(sqlQuery);
    } catch (err) {
        
        console.error("============ ERROR ==========\n", err.name, err.message);
    }
    await dbConnectionPool.close();
}
module.exports.createBranchMain = createBranchMain;


/*
브랜치 이벤트 sample
{
    "hook_event":"create",
    "source":"github",
    "action":"create_ref",
    "ref_type":"branch",
    "branch_name":"hhhhhhhhhhhhhhhhhhh",
    "author_id":"81180977",
    "repository_id":"510731046",
    "organization_id":"107110653",
}
*/