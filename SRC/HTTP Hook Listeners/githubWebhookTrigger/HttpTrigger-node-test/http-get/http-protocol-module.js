const request =require('request');
const axios = require('axios');
module.exports =  {
    async httpProtocolCustom(context, options){
        return request(options, function (err, response, body) {
            context.log("왜???? 여기 들어오긴 해????");
            if (err) {
                context.log(err);
            } else {
                context.log("response : " + response);
                context.log("진짜 왜 그러는거야");
                return JSON.parse(body);
            }
        });
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