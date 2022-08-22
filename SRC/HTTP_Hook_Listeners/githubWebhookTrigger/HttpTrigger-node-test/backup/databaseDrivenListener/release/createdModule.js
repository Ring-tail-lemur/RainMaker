async function createdMain(hookBody, cloudEventObj, context){
    return cloudEventObj;
}
module.exports.createdMain = createdMain;