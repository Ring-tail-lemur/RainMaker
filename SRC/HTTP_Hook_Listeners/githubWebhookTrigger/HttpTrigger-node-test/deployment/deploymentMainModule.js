async function deploymentMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.deployment = JSON.stringify(hookBody.deployment.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    return cloudEventObj;
}
module.exports.deploymentMain = deploymentMain;