async function completeMain(hookBody, cloudEventObj, context){
    cloudEventObj.event_time = JSON.stringify(hookBody.workflow_job.completed_at).replace(/['"]+/g, '');
    cloudEventObj.conclusion = JSON.stringify(hookBody.workflow_job.conclusion).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.completeMain = completeMain;