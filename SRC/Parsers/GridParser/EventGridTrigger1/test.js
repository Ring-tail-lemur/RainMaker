const index = require('./index.js');
const blobModule = require('./utils/blobModule.js');
const sql = require('mssql');
const configModule = require('./ms-sql/msSQLMakeConfig.js');
const controllerModule = require('./controllerModule.js');

// const jsonData = require('./_test_json_file/pullRequsetReviewTest.json');


const testData = {
    "hook_event": "pull_request",
    "X_GitHub_Delivery": "3caaa2e0-5f33-11ed-8e07-77d0975a3dc9",
    "event_triggered_time": "2022-11-8T07:02:01Z",
    "source": "github",
    "action": "labeled",
    "pull_request_remote_identifier": "1113807258",
    "repository_name": "test-for-fake-project",
    "repository_identifier": "510731046",
    "repository_owner_type": "Organization",
    "repository_owner_name": "Ring-tail-lemur",
    "repository_owner_id": "107110653",
    "repository_private": "false",
    "pull_request_label_id": "4298624576",
    "pull_request_label_name": "duplicate"
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
