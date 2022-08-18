async function pullRequestMain(context, hookBody, cloudEventObj){
    context.log(cloudEventObj.action + " Event Ocurred");
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.number = JSON.stringify(hookBody.number).replace(/['"]+/g, '');
    cloudEventObj.pull_request = JSON.stringify(hookBody.pull_request.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    
    return cloudEventObj;
}

module.exports.pullRequestMain = pullRequestMain;