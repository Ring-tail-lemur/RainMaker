const labellingModule = require("./issueLabeledModule");
const err_log_module = require('../utils/slackLogBot.js');
async function issueMain(hookBody, cloudEventObj, context){
    try{
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
        cloudEventObj.state = JSON.stringify(hookBody.issue.state).replace(/['"]+/g, '');
    
        try{
            //label이 되어있으면 그냥 집어넣자~
            cloudEventObj.label_name_list = await labellingModule.issueLabeling(hookBody, context);
            cloudEventObj.label_id_list = await labellingModule.issueLabelingId(hookBody, context);
            return cloudEventObj;
        }catch(e){
            return cloudEventObj;
        }
    }catch(e){
        err_log_module.log(e, "issueMainModule.js");
    }
}

module.exports.issueMain = issueMain;