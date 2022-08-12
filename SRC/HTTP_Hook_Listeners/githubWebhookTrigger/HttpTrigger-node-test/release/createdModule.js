const timeModule = require('../utils/getCurrentTimeModule.js');
async function createdMain(hookBody, cloudEventObj, context){
    cloudEventObj.event_time = await timeModule.getCurrentTime();
    return cloudEventObj;
}
module.exports.createdMain = createdMain;