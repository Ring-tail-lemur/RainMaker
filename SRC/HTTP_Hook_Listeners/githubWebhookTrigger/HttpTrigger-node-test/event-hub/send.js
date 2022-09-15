const { EventHubProducerClient } = require("@azure/event-hubs");
const fs = require("fs");

async function sender(cloudEventObj, context) {
    
  // const connectionString = 'Endpoint=sb://httptriggereventhubs.servicebus.windows.net/;SharedAccessKeyName=default;SharedAccessKey=ygtTa1wlgXx+UIr6up3i8x4aFHx2vNnD6NZ32K2W9gw=;EntityPath=githubhttpeventhub';
  const eventHubName = 'githubhttpeventhub';   
  // Create a producer client to send messages to the event hub.
  const connectionString = readJsonSecret(context);
  try{
    const producer = new EventHubProducerClient(connectionString, eventHubName, {"retryDelayInMs":60000});
  
    // Prepare a batch of three events.
    const batch = await producer.createBatch();
    batch.tryAdd({ body: cloudEventObj});
  
    // Send the batch to the event hub.
    await producer.sendBatch(batch);
  
    // Close the producer client.
    await producer.close();
    context.log(JSON.stringify(cloudEventObj));
  }catch (e) {
    context.log("err");
    context.log(e);
    // test
  }
}

async function readJsonSecret(context){
  //되라 이제..test...test...test...계속...
  context.log("hihi");
  const jsonFile = fs.readFileSync('event-hub-app-config.json','utf-8');
  context.log(jsonFile)
  const jsonData = JSON.parse(jsonFile);
  
  return jsonData.eventHubConnectionString;
}



module.exports.sender = sender;