const { EventHubProducerClient } = require("@azure/event-hubs");
const fs = require("fs");


async function sender(cloudEventObj, context) {

  const eventHubName = 'githubhttpeventhub';   
  // Create a producer client to send messages to the event hub.
  let connectionString = null;
  try{
    connectionString = await readJsonSecret(context);
  }catch(e){
    context.log(e);
  }
  connectionString = connectionString.slice(1,connectionString.length-1);
  context.log("-----------------------------------");
  context.log(connectionString === TestConnectionString);
  context.log(connectionString == TestConnectionString);
  context.log("-----------------------------------");
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
  }
}

async function readJsonSecret(context){
  const jsonFile = fs.readFileSync('.\\HttpTrigger-node-test\\event-hub\\event-hub-app-config.json','utf-8');
  context.log("jsonFile : ");
  context.log(jsonFile);
  
  try{
    const jsonData = JSON.parse(jsonFile);
    context.log("jsonData");
    context.log(JSON.stringify(jsonData.eventHubConnectionString));
    return JSON.stringify(jsonData.eventHubConnectionString);
  }catch(e){
    try{
      context.log("not Parsing");
      context.log(JSON.stringify(jsonFile.eventHubConnectionString));
      return JSON.stringify(jsonFile.eventHubConnectionString);
    }catch(e2){
      context.log(e2);
    }
  }
}


module.exports.sender = sender;