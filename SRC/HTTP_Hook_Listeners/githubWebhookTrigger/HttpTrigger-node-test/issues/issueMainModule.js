const issueLabeledModule = require('./issueLabeledModule.js');
const issueOpenedModule = require('./issueOpenedModule.js');
const issueEdittedModule = require('./issueEdittedModule.js');
const issueClosedModule = require('./issueClosedModule.js');
async function issueMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.issue_number = JSON.stringify(hookBody.issue.number).replace(/['"]+/g, '');
    cloudEventObj.issue_remote_id = JSON.stringify(hookBody.issue.id).replace(/['"]+/g, '');
    cloudEventObj.action_title = JSON.stringify(hookBody.issue.title).replace(/['"]+/g, '');
    cloudEventObj.actor_remote_id = JSON.stringify(hookBody.sender.id).replace(/['"]+/g, '');
    cloudEventObj.repository_remote_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');

    if(cloudEventObj.action == 'labeled'){
        return await issueLabeledModule.issueLabledMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'edited'){
        return await issueEdittedModule.issueEditedMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'opened'){
        return await issueOpenedModule.issueOpened(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'closed'){
        return await issueClosedModule.issueClosed(hookBody, cloudEventObj, context);
    }else{
        return cloudEventObj;
    }
}

module.exports.issueMain = issueMain;