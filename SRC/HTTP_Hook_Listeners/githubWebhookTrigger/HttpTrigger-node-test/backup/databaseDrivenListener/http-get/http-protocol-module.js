const axios = require('axios');

async function httpProtocolCustom(context, options) {
    const httpResult = await axios({
        method: 'GET',
        url: options.uri,
        headers: options.headers,
    });
    return httpResult.data;
}

module.exports.httpProtocolCustom = httpProtocolCustom;