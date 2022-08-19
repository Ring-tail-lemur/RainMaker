async function githubAppAuthorizationMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.sender =JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    return cloudEventObj;
}
module.exports.githubAppAuthorizationMain = githubAppAuthorizationMain;