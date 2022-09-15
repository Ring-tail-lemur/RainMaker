const { EventHubProducerClient } = require("@azure/event-hubs");
const fs = require("fs");
const process = require("process");

async function sender(cloudEventObj, context) {
  
  context.log(await listDir(process.cwd()));

  // const connectionString = 'Endpoint=sb://httptriggereventhubs.servicebus.windows.net/;SharedAccessKeyName=default;SharedAccessKey=ygtTa1wlgXx+UIr6up3i8x4aFHx2vNnD6NZ32K2W9gw=;EntityPath=githubhttpeventhub';
  const eventHubName = 'githubhttpeventhub';   
  
  // Create a producer client to send messages to the event hub.
  let connectionString = null;
  try{
    connectionString = await readJsonSecret(context);
  }catch(e){
    context.log(e);
  }
  
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
  const path = context.FunctionAppDirectory;
  context.log(path)
  const jsonFile = fs.readFileSync('.\\HttpTrigger-node-test\\event-hub\\event-hub-app-config.json','utf-8');
  context.log(jsonFile);
  const jsonData = JSON.parse(jsonFile);
  return jsonData.eventHubConnectionString;
}


async function listDir(tdir) {
  try {
    return await fs.promises.readdir(tdir);
  } catch (err) {
    console.error('Error occurred while reading directory!', err);
  }
}
module.exports.sender = sender;