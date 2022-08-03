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
    // context.log("--------- httpModule ---------\n"+JSON.stringify(commitsList.data)+"\n--------- httpModule ---------\n");
    return commitsList.data;
}



// httpProtocolCustom('',{uri: 'https://api.github.com/repos/Ring-tail-lemur/test-for-fake-project/pulls/170/commits',headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}});
module.exports.httpProtocolCustom = httpProtocolCustom;