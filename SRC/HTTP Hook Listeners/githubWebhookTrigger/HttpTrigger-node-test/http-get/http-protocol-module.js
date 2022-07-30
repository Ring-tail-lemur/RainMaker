const request =require('request');
const axios = require('axios');
module.exports =  {
    // async httpProtocolCustom(context, requestOptions){


    //     return new Promise(function (resolve, reject){


    //         request(requestOptions, function(err,response, body){
    //             if(err) reject(err);
    //             resolve(JSON.parse(body));
    //         });

    //     })
    // }
    async httpProtocolCustom(context, options){
        context.log("httpProtocolCustom");
        axios({
            method: 'GET',
            url: options.uri,
            headers: options.headers,
        }).then(function(response){
            context.log("response : " + response);
        }).catch(function(err){
            context.log(err);
        });
    },

    async testHTTP(context){
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
}   