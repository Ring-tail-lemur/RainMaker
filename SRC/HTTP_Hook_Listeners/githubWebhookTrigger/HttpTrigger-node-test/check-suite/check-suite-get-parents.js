const axios = require('axios');
async function checkSuiteGetParentWithToken(context, uri, cloudEventObj, inputToken){
    const options = {
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + inputToken}
    };
    const resultObj = await axios({
        method: 'GET',
        url: uri,
        headers: options.headers,
    })
    const firstParentSHA = JSON.stringify(resultObj.data.parents[0].sha);
    // context.log('firstParentSHA : ' + firstParentSHA);
    cloudEventObj.head_commit_parent_id = firstParentSHA.replace(/['"]+/g, '');
    return cloudEventObj;
}

async function checkSuiteGetParentWithoutToken(context, uri, cloudEventObj){
    const options = {
        headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
        };
    const resultObj = await axios({
        method: 'GET',
        url: uri,
        headers: options.headers,
    })
    const firstParentSHA = JSON.stringify(resultObj.data.parents[0].sha);
    // context.log('firstParentSHA : ' + firstParentSHA);
    cloudEventObj.head_commit_parent_id = firstParentSHA.replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.checkSuiteGetParentWithToken = checkSuiteGetParentWithToken;
module.exports.checkSuiteGetParentWithoutToken = checkSuiteGetParentWithoutToken;