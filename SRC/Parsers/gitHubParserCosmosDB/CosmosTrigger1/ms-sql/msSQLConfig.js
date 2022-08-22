module.exports = {
    port:1433,
    user:'rainmaker',
    password:'dkffkrRhfldudndnjstnddl!',
    server:'rainmaker-azure-sql-server.database.windows.net',
    database:'RainMakerSystemDB',
    options: {
      encrypt: true, // Use this if you're on Windows Azure
    },
    pool: { max: 10, min: 0, idleTimeoutMillis: 30000, },
    trustServerCertificate: true
}
