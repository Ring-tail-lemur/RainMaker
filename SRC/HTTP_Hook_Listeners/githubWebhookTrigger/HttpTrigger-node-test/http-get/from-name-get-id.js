const { delay } = require('@azure/core-amqp');
const axios = require('axios');
async function getUserIdFromName(name,context){
    //TODO github token 받는 부분
    const gitToken = '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';
    const uri = 'https://api.github.com/users/'+name;
    context.log(uri);
    const headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + gitToken
    }
    for(let i = 0; i< 3; i++){
        try{
            const body = await axios({
                method:'GET',
                url: uri,
                headers: headers,
            });
            context.log("get id"+body.data.id);
            return JSON.stringify(body.data.id);
        }catch(e){
            context.log("error ocurred in commit http get");
        }
        delay(100);
    }
}
module.exports.getUserIdFromName = getUserIdFromName;