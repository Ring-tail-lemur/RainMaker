async function workflowRunMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.workflow_run = JSON.stringify(hookBody.workflow_run.url).replace(/['"]+/g, '');
    cloudEventObj.workflow = JSON.stringify(hookBody.workflow.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
}
module.exports.workflowRunMain = workflowRunMain;