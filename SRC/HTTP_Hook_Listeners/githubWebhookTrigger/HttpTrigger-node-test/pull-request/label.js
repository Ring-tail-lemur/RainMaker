
async function pullRequestLabeled(hookBody, cloudEventObj){
    cloudEventObj.pull_request_label_id = JSON.stringify(hookBody.label.id).replace(/['"]]+/g, '');
    cloudEventObj.pull_request_label_name = JSON.stringify(hookBody.label.name).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.pullRequestLabeled = pullRequestLabeled;