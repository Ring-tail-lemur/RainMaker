const controllerModule = require('./controllerModule.js');
const sql = require('mssql');
const configModule = require('./ms-sql/msSQLMakeConfig.js');
const blobExtractor = require('./blob/blob-extractor.js');
module.exports = async function (context, eventGridEvent) {
    global.config = await configModule.makeConfig(context);
    const dbConnectionPool = new sql.ConnectionPool(config);
    try{
        await dbConnectionPool.connect();

        const fileUrl = eventGridEvent.data.fileUrl;
        context.log(fileUrl);
        const documents = await blobExtractor.blobModule(fileUrl,context);
        context.log(documents);
        // documents는 json모양의 object이다.
        for (const element of documents) {
            await controllerModule.controllerMain(element, context, dbConnectionPool);
        }
    }catch(e){
        context.log(e);
        context.log(eventGridEvent);
    }
};

