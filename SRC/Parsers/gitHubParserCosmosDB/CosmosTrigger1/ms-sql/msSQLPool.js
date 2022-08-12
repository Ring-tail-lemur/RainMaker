const sql = require('mssql');
const config = require('./msSQLConfig');

async function poolGetFunction(context) {

    const pool = new sql.ConnectionPool(config)
        .connect()
        .then(pool => {
            console.log('Connected to MSSQL')
            context.log("pool1", pool);
            return pool
        })
        .catch(err => context.log('Database Connection Failed! Bad Config: ', err))

    context.log("pool3", await pool);
    return pool;
}

// const pool = poolGetFunction(console);

const pool = new sql.ConnectionPool(config)
                    .connect()
                    .then(pool => {
                        console.log('Connected to MSSQL')
                        return pool
                    })
                    .catch(err => console.log('Database Connection Failed! Bad Config: ', err))

// const pool = new sql.ConnectionPool(config);

// module.exports.poolGetFunction = poolGetFunction;
module.exports = pool;