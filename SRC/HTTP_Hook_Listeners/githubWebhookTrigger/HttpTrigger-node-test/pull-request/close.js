const commit_module = require('../commit/commit-main.js');

async function pullRequestClose(context, hookBody, cloudEventObj){
    cloudEventObj.pull_request_closed_number = JSON.stringify(hookBody.number).replace(/['"]+/g, '');
    cloudEventObj.pull_request_opened_number = JSON.stringify(hookBody.pull_request.number).replace(/['"]+/g, '');
    cloudEventObj.pull_request_closed_time = JSON.stringify(hookBody.pull_request.closed_at).replace(/['"]+/g, '');
    cloudEventObj.pull_request_merged = JSON.stringify(hookBody.pull_request.merged_at) != null ? "true":false;
    cloudEventObj.pull_request_user_id = JSON.stringify(hookBody.pull_request.user.id).replace(/['"]+/g, '');
    cloudEventObj.private = JSON.stringify(hookBody.repository.private);
    const repositoryId = JSON.stringify(hookBody.repository.id);
    const commits_url = JSON.stringify(hookBody.pull_request.commits_url).replace(/['"]+/g, '');    
    cloudEventObj.pull_request_related_commit_count = await commit_module.commitMain(context, commits_url,cloudEventObj.private, cloudEventObj.pull_request_remote_identifier, repositoryId);
    return cloudEventObj;
}

module.exports.pullRequestClose = pullRequestClose;