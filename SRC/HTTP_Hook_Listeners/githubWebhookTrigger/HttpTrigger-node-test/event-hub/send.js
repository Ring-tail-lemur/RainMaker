const { EventHubProducerClient } = require("@azure/event-hubs");
const fs = require("fs");


async function sender(cloudEventObj, context) {

  const eventHubName = 'githubhttpeventhub';   
  
  let connectionString = null;
  try{
    connectionString = await readJsonSecret(context);
  }catch(e){
    context.log(e);
  }
  connectionString = connectionString.slice(1,connectionString.length-1);
  try{
    const producer = new EventHubProducerClient(connectionString, eventHubName, {"retryDelayInMs":60000});
  
    const batch = await producer.createBatch();
    batch.tryAdd({ body: cloudEventObj});
  
    await producer.sendBatch(batch);
  
    await producer.close();
    context.log(JSON.stringify(cloudEventObj));
  }catch (e) {
    context.log("err");
    context.log(e);
  }
}

async function readJsonSecret(context){
  const jsonFile = fs.readFileSync('.\\HttpTrigger-node-test\\event-hub\\event-hub-app-config.json','utf-8');
  
  try{
    const jsonData = JSON.parse(jsonFile);
    return JSON.stringify(jsonData.eventHubConnectionString);
  }catch(e){
    try{
      return JSON.stringify(jsonFile.eventHubConnectionString);
    }catch(e2){
      context.log(e2);
    }
  }
}


module.exports.sender = sender;