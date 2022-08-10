const queueModule = require('./workflowJobQueueModule');
const inProgressModule = require('./workflowJobInProgressModule');
const completeModule = require('./workflowJobCompleteModule');
async function workflowJobMain(hookBody, cloudEventObj, context) {
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.workflow_run_remote_id = JSON.stringify(hookBody.workflow_job.run_id).replace(/['"]+/g, '');
    cloudEventObj.workflow_job_remote_id = JSON.stringify(hookBody.workflow_job.id).replace(/['"]+/g, '');
    cloudEventObj.repository_remote_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
    cloudEventObj.repository.private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    cloudEventObj.actor_remote_id = JSON.stringify(hookBody.sender.id).replace(/['']+/g, '');
    if(cloudEventObj.action == 'queued'){
        return await queueModule.workflowJobQueueMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'in_progress'){
        return await inProgressModule.inProgressMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.action == 'completed'){
        return await completeModule.completeMain(hookBody, cloudEventObj, context);
    }
}
module.exports.workflowJobMain = workflowJobMain;