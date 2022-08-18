async function queueMain(hookBody, cloudEventObj, context) {
    cloudEventObj.event_time = JSON.stringify(hookBody.workflow_job.started_at).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.queueMain = queueMain;