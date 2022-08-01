const axios = require('axios');
const sendModule = require('../event-hub/send');
async function checkSuiteGetParentWithToken(context, uri, cloudEventObj, inputToken){
    const gitToken = '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';

    const options = {
        uri: uri,
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + gitToken}
    };
    await axios({
        method: 'GET',
        url: uri,
        headers: options.headers,
    }).then(function(response){
        const commitList = response.data;
        const firstParent = JSON.stringify(commitList.parents[0].sha);
        context.log(firstParent);
        return firstParent;
    }).catch(function(err){
        context.log(err);
    });
}

async function checkSuiteGetParentWithoutToken(context, uri, cloudEventObj){
    const options = {
        uri: uri,
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
        };
    await axios({
        method: 'GET',
        url: uri,
        headers: options.headers,
    }).then(function(response){
        const commitList = response.data;
        const firstParent = JSON.stringify(commitList.parents[0].sha);
        context.log(firstParent);
        cloudEventObj.head_commit_parent_id = firstParent.id;
        sendModule.sender(cloudEventObj, context);
    }).catch(function(err){
        context.log(err);
    });
}


module.exports.checkSuiteGetParentWithToken = checkSuiteGetParentWithToken;
module.exports.checkSuiteGetParentWithoutToken = checkSuiteGetParentWithoutToken;