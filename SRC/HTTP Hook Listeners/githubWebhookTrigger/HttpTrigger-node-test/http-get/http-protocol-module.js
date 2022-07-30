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
    }
}   