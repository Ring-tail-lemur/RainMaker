const commit_module = require('../commit/commit-main.js');

module.exports ={
    async pullRequestClose(context, hookBody, cloudEventObj){
        cloudEventObj.pull_request_closed_number = JSON.stringify(hookBody.number).replace(/['"]+/g, '');
        cloudEventObj.pull_request_opened_number = JSON.stringify(hookBody.pull_request.number).replace(/['"]+/g, '');
        cloudEventObj.pull_request_closed_time = JSON.stringify(hookBody.pull_request.closed_at).replace(/['"]+/g, '');
        cloudEventObj.pull_request_merged = JSON.stringify(hookBody.pull_request.merged_at) != null ? "true":false;
        const commits_url = JSON.stringify(hookBody.pull_request.commits_url).replace(/['"]+/g, '');    
        cloudEventObj.pull_request_related_commit_count = commit_module.commitMain(context, commits_url,cloudEventObj.private, cloudEventObj.pull_request_remote_identifier);
        return cloudEventObj;
    }
}