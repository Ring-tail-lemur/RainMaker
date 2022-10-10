const axios = require('axios');
const err_log_module = require('../utils/slackLogBot.js');
async function getPullRequestIdWithToken(context, url, gitTokenInput) {
    try{
        const options = {
        uri: url,
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + gitTokenInput}
        };
        await getModule.httpProtocolCustom(context, options);
        await axios({
            method: 'GET',
            url: options.uri,
            headers: options.headers,
        }).then(function(response){
            const pullRequestBody = response.data;
            const pullRequestRemoteId = JSON.stringify(pullRequestBody.id);
            return pullRequestRemoteId;
        }).catch(function(err){
            context.log(err);
        });
    }catch(e){
        err_log_module(e, "issue-pull-request.js || getPullRequestWithToken");
    }
}

async function getPullRequestIdWithoutToken(context, url){
    try{
        const options = {
        uri: url,
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
        };
        await getModule.httpProtocolCustom(context, options);
        await axios({
            method: 'GET',
            url: options.uri,
            headers: options.headers,
        }).then(function(response){
            const pullRequestBody = response.data;
            const pullRequestRemoteId = JSON.stringify(pullRequestBody.id);
            return pullRequestRemoteId;
        }).catch(function(err){
            context.log(err);
        });
    }catch(e){
        err_log_module.log(e, "issue-pull-request.js || getPullRequestWithoutToken");
    }
}
module.exports.getPullRequestIdWithToken = getPullRequestIdWithToken;
module.exports.getPullRequestIdWithoutToken = getPullRequestIdWithoutToken;