
async function pullRequestOpen(hookBody, cloudEventObj){
    cloudEventObj.pull_request_opened_number = JSON.stringify(hookBody.number).replace(/['"]]+/g, '');
    cloudEventObj.pull_request_open_branch = JSON.stringify(hookBody.pull_request.head.ref).replace(/['"]+/g, '');
    cloudEventObj.pull_request_close_branch = JSON.stringify(hookBody.pull_request.base.ref).replace(/['"]+/g, '');
    cloudEventObj.pull_request_open_time = JSON.stringify(hookBody.pull_request.created_at).replace(/['"]+/g, '');
    cloudEventObj.pull_request_user_id = JSON.stringify(hookBody.pull_request.user.id).replace(/['"]+/g, '');
    cloudEventObj.pull_request_additions = JSON.stringify(hookBody.pull_request.additions).replace(/['"]+/g, '');
    cloudEventObj.pull_request_deletions = JSON.stringify(hookBody.pull_request.deletions).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.pullRequestOpen = pullRequestOpen;
