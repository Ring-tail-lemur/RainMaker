const issueLabeledModule = require('./issueLabeledModule.js');
const issueOpenedModule = require('./issueOpenedModule.js');
const issueEditedModule = require('./issueEdittedModule.js');
const issueClosedModule = require('./issueClosedModule.js');
const labellingModule = require("./issueLabeledModule");
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
    cloudEventObj.action_time = JSON.stringify(hookBody.issue.updated_at).replace(/['"]+/g, '');

    try{
        //label이 되어있으면 그냥 집어넣자~
        cloudEventObj.labelNameList = await labellingModule.issueLabeling(hookBody, context);
        return cloudEventObj;
    }catch(e){
        return cloudEventObj;
    }


    // if(cloudEventObj.action == 'labeled'){
    //     return await issueLabeledModule.issueLabledMain(hookBody, cloudEventObj, context);
    // }else if(cloudEventObj.action == 'edited'){
    //     return await issueEditedModule.issueEditedMain(hookBody, cloudEventObj, context);
    // }else if(cloudEventObj.action == 'opened'){
    //     return await issueOpenedModule.issueOpenedMain(hookBody, cloudEventObj, context);
    // }else if(cloudEventObj.action == 'closed'){
    //     return await issueClosedModule.issueClosedMain(hookBody, cloudEventObj, context);
    // }else{
    //     return cloudEventObj;
    // }
}

module.exports.issueMain = issueMain;