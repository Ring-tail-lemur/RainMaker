async function inProgressMain(hookBody, cloudEventObj, context){
    cloudEventObj.event_time = JSON.stringify(hookBody.workflow_job.started_at);
    return cloudEventObj;
}

module.exports.inProgressMain = inProgressMain;