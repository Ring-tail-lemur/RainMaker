const blobExtractor = require('./blob/blob-extractor.js');
module.exports = async function (context, eventGridEvent) {
    const fileUrl = eventGridEvent.data.fileUrl;
    context.log(fileUrl);
    const objList = blobExtractor.blobModule(fileUrl);
    context.log(objList);
};

