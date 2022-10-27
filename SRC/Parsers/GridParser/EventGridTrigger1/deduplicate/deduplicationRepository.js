const err_log_module = require('../utils/slackLogBot.js');

async function checkDeduplication(dbConnectionPool, uuid) {

    const sqlQuery = `
    SELECT *
    FROM deduplication_check_table
    WHERE cosmosdb_id = ( '${uuid}' ) ;
    `;
    console.log(sqlQuery);

    let result;
    try {
        result = await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "duplicationRepository.js // checkDeduplication");
        console.error(e);
    }

    if(result.recordset[0]) {
        return true;
    }
    return false;
}

async function insertDeduplication(dbConnectionPool, uuid) {

    const sqlQuery = `
    INSERT INTO deduplication_check_table (cosmosdb_id)
    VALUES ('${uuid}');
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        err_log_module.log(e, "duplicationRepository.js // insertDeduplication");
        console.error(e);
    }
}



module.exports.checkDeduplication = checkDeduplication;
module.exports.insertDeduplication = insertDeduplication;