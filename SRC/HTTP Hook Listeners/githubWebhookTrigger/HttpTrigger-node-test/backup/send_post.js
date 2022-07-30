const request = require('request-promise-native');

module.exports = {
    async sender(cloudEventObj, context) {
      const gridUri = 'https://vidigummy.servicebus.windows.net/github-queue'; 
      
      // Create a producer client to send messages to the event hub.
      let options = {
        uri : gridUri,
        method : 'POST',
        headers : {
            'Content-Type': 'application/json'
        },
        body : cloudEventObj,
        json:true
      }
      try{
        request(options, function(err,res,body){
          context.log('JavaScript HTTP trigger function begun');
          var validationEventType = "Microsoft.EventGrid.SubscriptionValidationEvent";
      
          for (var events in req.body) {
              var body = req.body[events];
              // Deserialize the event data into the appropriate type based on event type
              if (body.data && body.eventType == validationEventType) {
                  context.log("Got SubscriptionValidation event data, validation code: " + body.data.validationCode + " topic: " + body.topic);
      
                  // Do any additional validation (as required) and then return back the below response
                  var code = body.data.validationCode;
                  context.res = { status: 200, body: { "ValidationResponse": code } };
              }
          }
          // context.done();
        });
      }catch (e) {
        context.log("err");
        context.log(e);
      }
    }
  };