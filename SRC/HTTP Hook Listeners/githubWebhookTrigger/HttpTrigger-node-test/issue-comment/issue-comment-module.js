const issuePullRequest = require('./issue-pull-request.js');
async function issueCommentMain(context, hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action);
    cloudEventObj.issue_comment_remote_id = JSON.stringify(hookBody.issue.id);
    cloudEventObj.issue_comment_repository_number = JSON.stringify(hookBody.issue.number);
    cloudEventObj.git_user_remote_id = JSON.stringify(hookBody.sender.id);
    cloudEventObj.event_time = JSON.stringify(hookBody.issue.created_at).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id);
    cloudEventObj.isPrivate = JSON.stringify(hookBody.repository.private);
    return cloudEventObj;

}

module.exports.issueCommentMain = issueCommentMain;