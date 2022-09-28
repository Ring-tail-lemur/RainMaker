const sql = require("mssql");
const fs = require("fs");
async function executeSqlQuery(query){
    sqlConfig =  await readJsonSecret('hi');
    console.log(query);
    console.log(sqlConfig);
    try{
        // make sure that any items are correctly URL encoded in the connection string
        const connectionPool = new sql.ConnectionPool(sqlConfig);
        await connectionPool.connect();
        const result = await connectionPool.request().query(query);
        console.dir(result.recordsets[0][0]['oauth_token']);
        connectionPool.close();
    } catch (err) {
        console.log(err);
    }
}
async function readJsonSecret(context){
    const jsonFile = fs.readFileSync('.\\ms-sql-config.json','utf-8');
    const config = JSON.parse(jsonFile);
    return config;
}
console.log("hi");
executeSqlQuery(`SELECT DISTINCT oauth_token FROM oauth_user WHERE user_remote_id = (SELECT DISTINCT oauth_user_id FROM oauth_user_repository_table WHERE repository_id =  514871723)`);


module.exports.executeSqlQuery = executeSqlQuery;
module.exports.readJsonSecret = readJsonSecret;