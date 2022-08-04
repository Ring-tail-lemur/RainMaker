
async function pullRequestOpen(hookBody, cloudEventObj){
    cloudEventObj.pull_request_opened_number = JSON.stringify(hookBody.number).replace(/['"]]+/g, '');
    cloudEventObj.pull_request_open_branch = JSON.stringify(hookBody.pull_request.head.ref).replace(/['"]+/g, '');
    cloudEventObj.pull_request_open_time = JSON.stringify(hookBody.pull_request.created_at).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.pullRequestOpen = pullRequestOpen;