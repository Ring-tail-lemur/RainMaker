const fs = require("fs");
async function makeConfig(){
    const ConfigClass = await readJsonSecret();
    msConfig = {
        port : parseInt(JSON.stringify(ConfigClass.port).replace(/['"]+/g, '')),
        user : JSON.stringify(ConfigClass.user).replace(/['"]+/g, ''),
        password : JSON.stringify(ConfigClass.password).replace(/['"]+/g, ''),
        server : JSON.stringify(ConfigClass.server).replace(/['"]+/g, ''),
        database : JSON.stringify(ConfigClass.database).replace(/['"]+/g, ''),
        options: {
            encrypt: true, // Use this if you're on Windows Azure
        },
        pool: { max: 10, min: 0, idleTimeoutMillis: 30000, },
        trustServerCertificate: true
    };
    return msConfig;
}
async function readJsonSecret(){
    const jsonFile = fs.readFileSync('.\\CosmosTrigger1\\ms-sql-config.json','utf-8');
    const jsonData = JSON.parse(jsonFile);
    return jsonData;
}
module.exports.makeConfig = makeConfig;