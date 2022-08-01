const request =require('request');
const axios = require('axios');
const parsingModule = require('../commit/commit-parsing.js');

async function httpProtocolCustom(context, options,pull_request_remote_identifier) {
    await axios({
        method: 'GET',
        url: options.uri,
        headers: options.headers,
    }).then(function(response){
        const commitList = response.data;
        const commitListLength = commitList.length;
        for(i = 0; i < commitListLength; i++){
            parsingModule.parsingCommit(context, commitList[i], pull_request_remote_identifier)
        }
    }).catch(function(err){
        context.log(err);
    });
}
async function testHTTP(context){
    const uri = 'https://api.github.com/repos/Ring-tail-lemur/RainMaker/pulls/13/commits';
    const token = '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';
    const options = {
    uri: uri,
    headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + token}
    };
    request(options, function(err,response,body){
    if(err){
        context.log(err);
    }else{
        const jsoned = JSON.parse(body);
        const jsonedLength = jsoned.length;
        for(i = 0; i < jsonedLength; i++){
            context.log(jsoned[i]);
        }
    }
    });
}


module.exports.httpProtocolCustom = httpProtocolCustom;
module.exports.testHTTP = testHTTP;