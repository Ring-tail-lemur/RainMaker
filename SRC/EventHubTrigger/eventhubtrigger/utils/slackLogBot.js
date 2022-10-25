const axios = require('axios');

async function sendErrMessage(e, now){
    const sqlConfig =  await readJsonSecret('hi');
    const message = await makeMessage(e,now);
    const uri = JSON.stringify(sqlConfig.slack-bot-uri).replace(/['"]+/g, '');
    axios.post(uri,{"text": `${message}`});
}

async function makeMessage(err, now){
    const message = `
    Github Listener Error Ocurred
    --------------------------------------
    Error Location : ${now}
    --------------------------------------
    Error message : ${err}
    `
    return message;
}

async function readJsonSecret(context){
    const jsonFile = fs.readFileSync('.\\slack-config.json','utf-8');
    const config = JSON.parse(jsonFile);
    return config;
}

module.exports.log = sendErrMessage;