const httpModule = require('../http-get/http-protocol-module.js');

async function getPullRequestMain(inputUri, isPrivate, context){
    const tmpHeaders = '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'
    if(isPrivate == 'true'){
        const options = {
            uri: inputUri,
            headers: {
                'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit',
                'Authorization': 'Bearer '+tmpHeaders
            }
        }
        const url_data = await httpModule.request(context,options);
        return JSON.stringify(url_data.id).replace(/['"]+/g, '');
    }else{
        const options = {
            uri: inputUri,
            headers: {
                'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit',
            }
        }
        const url_data = await httpModule.request(context,options);
        return JSON.stringify(url_data.id).replace(/['"]+/g, '');
    }
}
module.exports.getPullRequestMain = getPullRequestMain;