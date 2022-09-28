const sql = require("mssql");
const fs = require("fs");
async function readJsonSecret(context){
    const jsonFile = fs.readFileSync('.\\ms-sql-config.json','utf-8');
    const config = JSON.parse(jsonFile);
    return config;
}



async function executeSqlQuery(query){
    sqlConfig =  await readJsonSecret('hi');
    // console.log(query);
    // console.log(sqlConfig);
    try{
        // make sure that any items are correctly URL encoded in the connection string
        const connectionPool = new sql.ConnectionPool(sqlConfig);
        await connectionPool.connect();
        const result = await connectionPool.request().query(query);
        if(result.recordsets != null){
            return result.recordsets;
        }else{
            throw new Error;
        }
    } catch (err) {
        console.log(err);
    }
}

async function getTokenByRepositoryId(repositoryId,context){
    newQuery = `SELECT DISTINCT oauth_token FROM oauth_user WHERE user_remote_id = (SELECT DISTINCT oauth_user_id FROM oauth_user_repository_table WHERE repository_id =  ${repositoryId})`;
    const queryResult =  await executeSqlQuery(newQuery); 
    console.log(queryResult[0][0]['oauth_token'])
    return queryResult[0][0]['oauth_token']
}

getTokenByRepositoryId(514871723,'');
// console.log(result);

module.exports.getTokenByRepositoryId = getTokenByRepositoryId;