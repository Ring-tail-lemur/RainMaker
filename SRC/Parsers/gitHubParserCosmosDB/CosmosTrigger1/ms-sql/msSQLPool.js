const sql = require('mssql');
const config = require('./msSQLConfig');

const pool = new sql.ConnectionPool(config)
                    .connect()
                    .then(pool => {
                        console.log('Connected to MSSQL')
                        return pool
                    })
                    .catch(err => console.log('Database Connection Failed! Bad Config: ', err))

module.exports = pool;