const controllerModule = require('./controllerModule.js');
const sql = require('mssql');
const configModule = require('./ms-sql/msSQLMakeConfig.js');
//ci test
module.exports = async function (context, documents) {
    
    global.config = await configModule.makeConfig(context);
    const dbConnectionPool = new sql.ConnectionPool(config);
    try {
        await dbConnectionPool.connect();

        // context.log("DBConnection \n", dbConnectionPool.pool);

        if (!!documents && documents.length > 0) {
            for (const element of documents) {
                await controllerModule.controllerMain(element, context, dbConnectionPool);
            }
        }
    } catch (e) {
        context.log(e);
    } finally {
        dbConnectionPool.close();
        context.res = {
            body: "ok"
        }
    }


}


/*
cosmosDB의 데이터 예시(create issue_comment -> 이건 아직 사용을 안 해서 버린다. do nothing.)
여기서 중요한 것은 id(cosmosDB에서 유일한 값), _lsn(해당 cosmosDB에서 송출된 번호(순서라고 생각해도 됨))
위에서 document.length에 따라 for문을 돌린 것은 one document one event가 아닌, batch된 event가 들어오기 때문이다.참고 바람.
{
    "id": "a0c8e24e-978d-4cbf-a631-dc094a9e4101",
    "_rid": "6LZfAJRxZZmbAAAAAAAAAA==",
    "_self": "dbs/6LZfAA==/colls/6LZfAJRxZZk=/docs/6LZfAJRxZZmbAAAAAAAAAA==/",
    "_ts": 1659523910,
    "_etag":"0000a400-0000-2500-0000-62ea53460000",
    "hook_event": "issue_comment",
    "source": "github",
    "action": "created",
    "issue_comment_remote_id": "1327018333",
    "issue_comment_repository_number": "196",
    "git_user_remote_id": "33488236",
    "event_time": "2022-08-03T10'39'21Z",
    "repository_id": "510731046",
    "isPrivate": "false",
    "EventProcessedUtcTime": "2022-08-03T10'51'49.5033842Z",
    "PartitionId": 2,
    "EventEnqueuedUtcTime": "2022-08-03T10'51'49.273Z",
    "_lsn": 113
}
test

*/