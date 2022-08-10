const requestModule = require('./workflowRunRequestedModule.js');
const completeModule = require('./workflowRunCompletedModule.js');
//
async function workflowRunMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.workflow_run_remote_id = JSON.stringify(hookBody.workflow_run.id).replace(/['"]+/g, '');
    cloudEventObj.workflow_remote_id = JSON.stringify(hookBody.workflow.workflow_id).replace(/['"]+/g, '');
    cloudEventObj.workflow_head_branch = JSON.stringify(hookBody.workflow.head_branch).replace(/['"]+/g, '');
    cloudEventObj.workflow_run_number = JSON.stringify(hookBody.workflow.run_number).replace(/['"]+/g, '');
    cloudEventObj.workflow_check_suite_id = JSON.stringify(hookBody.workflow.check_suite_id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
    cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    cloudEventObj.head_repository_id = JSON.stringify(hookBody.workflow_run.head_repository.id).replace(/['"]+/g, '');
    cloudEventObj.head_commit_id = JSON.stringify(hookBody.workflow_run.head_commit.id).replace(/['"]+/g, '');
    if(cloudEventObj.action == 'requested'){
        return await requestModule.workflowRunRequestMain(hookBody,cloudEventObj,context);
    }else if(cloudEventObj.action == 'completed'){
        return await completeModule.workflowRunCompleteMain(hookBody,cloudEventObj,context);
    }else{
        context.log(cloudEventObj.action + ' ' + "action is not yet checked&&developed!");
        return cloudEventObj;
    }
}
module.exports.workflowRunMain = workflowRunMain;