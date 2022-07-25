const { EventHubProducerClient } = require("@azure/event-hubs");



module.exports = {
  async sender(cloudEventObj, context) {
    const connectionString = 'Endpoint=sb://eventhubtest-vidigummy.servicebus.windows.net/;SharedAccessKeyName=test;SharedAccessKey=WGKN71DKs9z9OCZ0ysirCIiRCQLT0IbbBh3VP6Obu5A=;EntityPath=eventhub-vidigummy';
    const connectionString2 = 'Endpoint=sb://eventhubtest-vidigummy.servicebus.windows.net/;SharedAccessKeyName=test;SharedAccessKey=2EQr3DJ/dhsSdLwbU1zI06VZraS87RayNj/iPzbQ8ZY=;EntityPath=eventhub-bigger-partition-vidigummy';
    const eventHubName = 'eventhub-bigger-partition-vidigummy';   
    // Create a producer client to send messages to the event hub.
    try{
      const producer = new EventHubProducerClient(connectionString2, eventHubName);
    
      // Prepare a batch of three events.
      const batch = await producer.createBatch();
      batch.tryAdd({ body: cloudEventObj});
    
      // Send the batch to the event hub.
      await producer.sendBatch(batch);
    
      // Close the producer client.
      await producer.close();
    
      context.log("A batch of three events have been sent to the event hub");
  
    }catch (e) {
      context.log("err");
      context.log(e);
    }
  }
};




