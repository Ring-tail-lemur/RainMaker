const blobModule = require('./utils/blobModule.js');
module.exports = async function (context, eventGridEvent) {
    context.log(typeof eventGridEvent);
    context.log(eventGridEvent);
    context.log("test");
    const fileUrl = eventGridEvent.data.fileUrl;
    context.log(fileUrl);
    const documents = await blobModule.blobExtract(fileUrl, context);
    context.log(documents);
};