const err_log_module = require('../utils/slackLogBot.js');
async function pullRequestReviewMain(hookBody, cloudEventObj){
    try{
        cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
        cloudEventObj.event_time = JSON.stringify(hookBody.review.submitted_at).replace(/['"]+/g, '');
        cloudEventObj.actor_remote_id = JSON.stringify(hookBody.sender.id);
        cloudEventObj.pull_request_remote_identifier = JSON.stringify(hookBody.pull_request.id);
        cloudEventObj.review_state = JSON.stringify(hookBody.review.state).replace(/['"]+/g, '');
        cloudEventObj.review_id = JSON.stringify(hookBody.review.id).replace(/['"]+/g, '');
        return cloudEventObj;
    }catch(e){
        err_log_module.log(e, "pull-request-review-module.js");
    }
}
module.exports.pullRequestReviewMain = pullRequestReviewMain;