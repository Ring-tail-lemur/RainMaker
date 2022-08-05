const { EventHubProducerClient } = require("@azure/event-hubs");

module.exports = {
  async sender(cloudEventObj, context) {
    const connectionString = 'Endpoint=sb://httptriggereventhubs.servicebus.windows.net/;SharedAccessKeyName=default;SharedAccessKey=ygtTa1wlgXx+UIr6up3i8x4aFHx2vNnD6NZ32K2W9gw=;EntityPath=githubhttpeventhub';
    const eventHubName = 'githubhttpeventhub';   
    // Create a producer client to send messages to the event hub.
    context.log(JSON.stringify(cloudEventObj));
    try{
      const producer = new EventHubProducerClient(connectionString, eventHubName);
    
      // Prepare a batch of three events.
      const batch = await producer.createBatch();
      batch.tryAdd({ body: cloudEventObj});
    
      // Send the batch to the event hub.
      await producer.sendBatch(batch);
    
      // Close the producer client.
      await producer.close();
  
    }catch (e) {
      context.log("err");
      context.log(e);
      // test
    }
    
    
  }
};




