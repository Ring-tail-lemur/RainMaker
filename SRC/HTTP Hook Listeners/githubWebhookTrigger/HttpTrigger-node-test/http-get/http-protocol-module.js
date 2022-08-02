const request =require('request');
const axios = require('axios');
const parsingModule = require('../commit/commit-parsing.js');

async function httpProtocolCustom(context, options) {
    const commitsList = await axios({
        method: 'GET',
        url: options.uri,
        headers: options.headers,
    });
    return commitsList;
}

module.exports.httpProtocolCustom = httpProtocolCustom;