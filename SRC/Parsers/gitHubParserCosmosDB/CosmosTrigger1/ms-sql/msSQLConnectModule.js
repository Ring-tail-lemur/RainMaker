const { Connection, Request } = require("tedious");

// Create connection to database
const config = {
    server: "rainmaker-azure-sql-server.database.windows.net", // update me
    authentication: {
        type: "default",
        options: {
        userName: "rainmaker", // update me
        password: 'dkffkrRHfldudndnjstnddl!' // update me
        },
    },
    options: {
        database: "rainmaker-azure-sql-database", //update me
        encrypt: true
    }
};

// Attempt to connect and execute queries if connection goes through
var connection = new Connection(config);  
connection.on('connect', function(err) {  
    // If no error, then good to proceed.
    console.log("Connected");  
});

    
connection.connect();