const request =require('request');
const axios = require('axios');
const parsingModule = require('../commit/commit-parsing.js');

async function httpProtocolCustom(context, options) {
    context.log(options);
    const commitsList = await axios({
        method: 'GET',
        url: options.uri,
        headers: options.headers,
    });
    context.log("--------- httpModule ---------\n"+commitList+"\n--------- httpModule ---------\n");
    return commitsList;
}




module.exports.httpProtocolCustom = httpProtocolCustom;