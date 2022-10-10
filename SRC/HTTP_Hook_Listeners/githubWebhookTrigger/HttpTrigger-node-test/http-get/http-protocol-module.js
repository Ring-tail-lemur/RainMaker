const axios = require('axios');
const err_log_module = require('../utils/slackLogBot.js');
async function httpProtocolCustom(context, options) {
    try{
        const httpResult = await axios({
            method: 'GET',
            url: options.uri,
            headers: options.headers,
        });
        return httpResult.data;
    }catch(e){
        err_log_module.log(e,"http-protocol-module.js");
    }
}

module.exports.httpProtocolCustom = httpProtocolCustom;