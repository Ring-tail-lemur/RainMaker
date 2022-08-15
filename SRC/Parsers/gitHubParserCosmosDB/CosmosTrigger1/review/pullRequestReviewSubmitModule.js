const pullRequestReviewCreateRepository = require('./pullRequestReviewCreateRepository');

async function pullRequestSubmitModule(pool, eventObject, context){
    // pull_request_event entity 생성 및 삽입
    await pullRequestReviewCreateRepository.insertPullRequestCommentByPullRequestIdAndUserId(pool, context, eventObject.event_time, eventObject.pull_request_remote_identifier, eventObject.actor_remote_id, eventObject.review_state);
}

module.exports.pullRequestSubmitModule = pullRequestSubmitModule;

/*
{
    "hook_event": "pull_request_review", 
    "source": "github", 
    "action": "submitted", 
    "event_time": "2022-08-02T07:46:43Z", 
    "actor_remote_id": "33488236", 
    "pull_request_remote_identifier": "1014736314", 
    "review_state": "commented",

    "EventProcessedUtcTime": "2022-08-02T07:46:45.5451905Z", 
    "PartitionId": 1, 
    "EventEnqueuedUtcTime": "2022-08-02T07:46:45.321Z"
}
*/