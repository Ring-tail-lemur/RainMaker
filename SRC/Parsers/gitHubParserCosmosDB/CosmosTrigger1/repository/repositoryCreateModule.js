const pool = require('../ms-sql/msSQLPool');

async function repositoryCreateMain(eventObject, context){
    //repository entity 생성 및 삽입
    console.log("eventObject : \n", eventObject);
    const dbpool = await pool;
    const result = await dbpool.request()
        .query(`SELECT * FROM dbo.commits where commit_id = ${1}`)

    console.log("result", result);

    await dbpool.close();
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
*/