const sql = require("mssql");
const fs = require("fs");
const err_log_module = require('../utils/slackLogBot.js');
async function readJsonSecret(context){
    const jsonFile = fs.readFileSync('.\\ms-sql-config.json','utf-8');
    const config = JSON.parse(jsonFile);
    return config;
}



async function executeSqlQuery(query){
    let sqlConfig =  await readJsonSecret();
    try{
        // make sure that any items are correctly URL encoded in the connection string
        const connectionPool = new sql.ConnectionPool(sqlConfig);
        await connectionPool.connect();
        const result = await connectionPool.request().query(query);
        connectionPool.close();
        if(result.recordsets != null){
            return result.recordsets;
        }else{
            throw new Error;
        }
    } catch (err) {
        err_log_module.log(err, "msSQLModule.js");
    }
}

async function getTokenByRepositoryId(repositoryId,context){
    const newQuery = `SELECT TOP 1 oauth_token FROM oauth_user WHERE user_remote_id = (SELECT TOP 1 oauth_user_id FROM oauth_user_repository_table WHERE repository_id =  ${Number(repositoryId)})`;
    const queryResult =  await executeSqlQuery(newQuery);
    return queryResult[0][0]['oauth_token']
}

module.exports.getTokenByRepositoryId = getTokenByRepositoryId;