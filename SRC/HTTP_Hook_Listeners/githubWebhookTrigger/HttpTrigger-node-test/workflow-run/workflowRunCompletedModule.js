async function workflowRunCompleteMain(hookBody, cloudEventObj, context){
    cloudEventObj.conclusion = JSON.stringify(hookBody.workflow_run.conclusion).replace(/['"]+/g, '');
    cloudEventObj.event_time = JSON.stringify(hookBody.workflow_run.updated_at).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.workflowRunCompleteMain = workflowRunCompleteMain;