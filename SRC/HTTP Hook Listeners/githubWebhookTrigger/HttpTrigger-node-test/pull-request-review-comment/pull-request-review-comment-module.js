async function pullRequestReviewCommentMain(context, hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.event_time = JSON.stringify(hookBody.comment.created_at);
    cloudEventObj.action_id = JSON.stringify(hookBody.comment.id);
    cloudEventObj.review_id = JSON.stringify(hookBody.comment.pull_request_review_id);
    cloudEventObj.action_id = JSON.stringify(hookBody.sender.id);
    cloudEventObj.pull_request_remote_id = JSON.stringify(hookBody.pull_request.id);
    return cloudEventObj;
}

module.exports.pullRequestReviewCommentMain = pullRequestReviewCommentMain;