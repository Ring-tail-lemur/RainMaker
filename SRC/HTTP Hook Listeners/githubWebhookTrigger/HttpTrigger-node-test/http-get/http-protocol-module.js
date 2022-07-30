const request =require('request');
const axios = require('axios');
module.exports =  {
    async httpProtocolCustom(context, requestOptions){


        return new Promise(function (resolve, reject){
            let options = {
                url: requestOptions.url,
                headers:{
                    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'
                }
            }

            request(options, function(err,response, body){
                if(err) reject(err);
                resolve(JSON.parse(body));
            });

        })
    }
    // async httpProtocolCustom(context, options){
    //     axios({
    //         method: 'GET',
    //         url: options.uri,
    //         headers: options.headers,
    //     }).then(function(response){
    //         context.log("response : " + response);
    //     }).catch(function(err){
    //         context.log(err);
    //     });
    // }
}   