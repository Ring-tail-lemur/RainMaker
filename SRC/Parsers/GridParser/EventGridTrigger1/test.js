const index = require('./index.js');
const blobModule = require('./utils/blobModule.js');
const sql = require('mssql');
const configModule = require('./ms-sql/msSQLMakeConfig.js');
const controllerModule = require('./controllerModule.js');

// const jsonData = require('./_test_json_file/pullRequsetReviewTest.json');


const testData = {
    "hook_event": "issues",
    "X_GitHub_Delivery": "42b79e30-600b-11ed-94f3-15dab9b0fcd2",
    "event_triggered_time": "2022-11-9T08:48:24Z",
    "source": "github",
    "action": "opened",
    "issue_number": "299",
    "issue_remote_id": "1441676893",
    "action_title": "fdgfs",
    "actor_remote_id": "68802559",
    "repository_remote_id": "510731046",
    "repository_private": "false",
    "repository_owner_type": "Organization",
    "repository_owner_id": "107110653",
    "action_time": "2022-11-09T08:48:19Z",
    "state": "open",
    "label_name_list": "[RainMaker]runtime-error-new",
    "label_id_list": "4695699824"
}


const a = []
a.push(testData);


const context = console

hi = async (testData) => {
    global.config = await configModule.makeConfig(context);
    const dbConnectionPool = new sql.ConnectionPool(config);
    try {
        await dbConnectionPool.connect();
        await controllerModule.controllerMain(testData, console, dbConnectionPool);
    } catch (e) {
        context.log(e);
    }
}

hi(testData)
