const blobModule = require('./utils/blobModule.js');
const sql = require('mssql');
const configModule = require('./ms-sql/msSQLMakeConfig.js');
const controllerModule = require('./controllerModule.js');
module.exports = async function (context, eventGridEvent) {
    global.config = await configModule.makeConfig(context);
    const dbConnectionPool = new sql.ConnectionPool(config);
    try{
        await dbConnectionPool.connect();
        const fileUrl = eventGridEvent.data.fileUrl;
        context.log(fileUrl);
        const documents = await blobModule.blobExtract(fileUrl, context);
        for (const document of documents) {
            await controllerModule.controllerMain(document, context, dbConnectionPool);
        }
    }catch(e){
        context.log(e);
    }
    
    
};