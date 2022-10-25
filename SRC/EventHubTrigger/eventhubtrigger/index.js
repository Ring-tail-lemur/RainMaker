const blobExtractor = require('./blob/blob-extractor.js');
module.exports = async function (context, eventGridEvent) {
    // context.log(eventGridEvent);
    try{
        const fileUrl = eventGridEvent.data.fileUrl;
        context.log(fileUrl);
        const objList = await blobExtractor.blobModule(fileUrl,context);
        context.log(objList);
    }catch(e){
        context.log(e);
        context.log(eventGridEvent)
    }
};

