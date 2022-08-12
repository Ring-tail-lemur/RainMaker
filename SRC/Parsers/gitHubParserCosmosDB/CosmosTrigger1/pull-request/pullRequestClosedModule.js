const pullRequestCreateRepository = require('./pullRequestCreateRepository');

async function pullRequestCloseMain(eventObject, context) {
    //pull_request_event entity 생성 및 삽입
    await pullRequestCreateRepository.insertPullRequestEventClosedByPullRequestIdAndUserId(eventObject.action, eventObject.pull_request_closed_time, eventObject.pull_request_remote_identifier, eventObject.pull_request_user_id);
    //pull_request_direction entity 생성 및 삽입
    await pullRequestCreateRepository.insertPullRequestDirectionBySourcePullRequestId(eventObject.pull_request_remote_identifier);
}

module.exports.pullRequestCloseMain = pullRequestCloseMain;
/*
pullRequestClosed 이벤트 sample
{
    "hook_event": "pull_request", 
    "source": "github", 
    "action": "closed", 
    "pull_request_remote_identifier": "1014736314", 
    "repository_name": "test-for-fake-project", 
    "repository_identifier": "510731046", 
    "repository_owner_type": "Organization", 
    "repository_owner_name": "Ring-tail-lemur", 
    "repository_owner_id": "107110653", 
    "repository_private": "false", 
    "pull_request_closed_number": "184", 
    "pull_request_opened_number": "184", 
    "pull_request_closed_time": "2022-08-02T07:44:23Z", 
    "pull_request_merged": "true", 
    "private": "false", 
    "pull_request_related_commit_count": 8,
    "pull_request_user_id" :"123456",

    "EventProcessedUtcTime": "2022-08-02T07:44:26.987551Z", 
    "PartitionId": 0, 
    "EventEnqueuedUtcTime": "2022-08-02T07:44:26.916Z"
}
*/