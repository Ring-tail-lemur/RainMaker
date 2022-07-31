const issuePullRequest = require('./issue-pull-request.js');
async function issueCommentMain(context, hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action);
    cloudEventObj.git_user_remote_id = JSON.stringify(hookBody.sender.id);
    cloudEventObj.event_time = JSON.stringify(hookBody.issue.created_at).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id);

    const pull_request_url = JSON.stringify(hookBody.issue.pull_request.url).replace(/['"]+/g, '');
    cloudEventObj.isPrivate = JSON.stringify(hookBody.repository.private);
    if(cloudEventObj.isPrivate == "true"){
        cloudEventObj.pull_request_remote_id = await issuePullRequest.getPullRequestIdWithToken(context, pull_request_url, '');
    }else{
        cloudEventObj.pull_request_remote_id = await issuePullRequest.getPullRequestIdWithoutToken(context, pull_request_url);
    }
    return cloudEventObj;

}

module.exports.issueCommentMain = issueCommentMain;