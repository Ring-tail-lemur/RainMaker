const request =require('request');
module.exports =  {
    async httpProtocolCustom(context, options){
        await request(options, async function(err,response,body){
          context.log("왜???? 여기 들어오긴 해????");
          if(err){
              context.log(err);
          }else{
              context.log("response : "  + response);
              context.log("진짜 왜 그러는거야");
              return await JSON.parse(body);
          }
        });
    }
}   