async function workflowJobMain(hookBody, cloudEventObj, context) {
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.workflow_job = JSON.stringify(hookBody.workflow_job.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
}
module.exports.workflowJobMain = workflowJobMain;
