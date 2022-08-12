const pool = require('./ms-sql/msSQLPool');

async function close(context){
    const dbConnectionPool = await pool;
    context.log("DBConnection10 ================\n", dbConnectionPool.pool);
    await dbConnectionPool.close();
}

module.exports.close = close;