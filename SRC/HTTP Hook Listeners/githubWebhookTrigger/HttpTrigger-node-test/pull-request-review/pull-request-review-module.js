async function pullRequestReviewMain(context, hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.event_time = JSON.stringify(hookBody.review.submitted_at);
    cloudEventObj.actor_remote_id = JSON.stringify(hookBody.sender.id);
    cloudEventObj.pull_request_remote_identifier = JSON.stringify(hookBody.pull_request.id);
    cloudEventObj.review_state = JSON.stringify(hookBody.review.state);
    return cloudEventObj;
}
module.exports.pullRequestReviewMain = pullRequestReviewMain;