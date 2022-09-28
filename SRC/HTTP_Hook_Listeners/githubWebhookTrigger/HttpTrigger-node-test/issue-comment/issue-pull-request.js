const axios = require('axios');

async function getPullRequestIdWithToken(context, url, gitTokenInput) {
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
}

async function getPullRequestIdWithoutToken(context, url){
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
}
module.exports.getPullRequestIdWithToken = getPullRequestIdWithToken;
module.exports.getPullRequestIdWithoutToken = getPullRequestIdWithoutToken;