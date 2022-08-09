const issueLabeledModule = require('./issuesLabeledModule.js');
async function issuesMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.issue_number = JSON.stringify(hookBody.issue.number).replace(/['"]+/g, '');
    cloudEventObj.issue_remote_id = JSON.stringify(hookBody.issue.id).replace(/['"]+/g, '');
    cloudEventObj.action_title = JSON.stringify(hookBody.issue.title).replace(/['"]+/g, '');
    cloudEventObj.repository_remote_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');

    if(cloudEventObj.action == 'labeled'){
        return await issueLabeledModule.issueLabledMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'edited'){

    }else if(cloudEventObj.action == 'opened'){
    }else if(cloudEventObj.action == 'closed'){

    }else{
        return cloudEventObj;
    }
}

module.exports.issuesMain = issuesMain;