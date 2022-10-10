const httpModule = require('../http-get/http-protocol-module.js');
const err_log_module = require('../utils/slackLogBot.js');
async function getPullRequestMain(inputUri, isPrivate, context, accessToken){
    const tmpHeaders = accessToken;
    try{
        if(isPrivate == "true"){
            const options = {
                uri: inputUri,
                headers: {
                    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit',
                    'Authorization': 'Bearer '+tmpHeaders
                }
            }
            const url_data = await httpModule.httpProtocolCustom(context,options);
            return JSON.stringify(url_data.id).replace(/['"]+/g, '');
        }else{
            const options = {
                uri: inputUri,
                headers: {
                    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit',
                }
            }
            const url_data = await httpModule.httpProtocolCustom(context,options);
            return JSON.stringify(url_data.id).replace(/['"]+/g, '');
        }
    }catch(e){
        err_log_module.log(e,"getPullRequestModule.js");
    }
}
module.exports.getPullRequestMain = getPullRequestMain;