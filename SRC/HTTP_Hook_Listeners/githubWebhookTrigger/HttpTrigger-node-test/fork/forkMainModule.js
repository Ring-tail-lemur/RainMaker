async function forkMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = 'fork';
    cloudEventObj.forkee = JSON.stringify(hookBody.forkee.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    return cloudEventObj;
}
module.exports.forkMain = forkMain;