async function workflowRunRequestMain(hookBody, cloudEventObj,context){
    cloudEventObj.event_time = JSON.stringify(hookBody.workflow_run.run_started_at).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.workflowRunRequestMain = workflowRunRequestMain;