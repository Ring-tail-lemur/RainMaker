const pullRequestCreateRepository = require('./pullRequestCreateRepository');

async function pullRequestOpenMain(pool, eventObject, context) {

    //pull_request entity 생성 및 삽입
    const pullRequest_id = await pullRequestCreateRepository.insertPullRequestByRepoIdAndBranchId(pool, eventObject.pull_request_remote_identifier, eventObject.pull_request_opened_number, eventObject.repository_identifier, eventObject.pull_request_open_branch, eventObject.pull_request_close_branch);
    //pull_request_event entity 생성 및 삽입
    await pullRequestCreateRepository.insertPullRequestEventOpenByPullRequestIdAndUserId(pool, eventObject.action, eventObject.pull_request_open_time, pullRequest_id, eventObject.pull_request_user_id);

}

module.exports.pullRequestOpenMain = pullRequestOpenMain;

/*
pullRequestOpen 이벤트 sample
{
    "hook_event":"pull_request",
    "source":"github",
    "action":"opened",
    "pull_request_remote_identifier":"1021411142",
    "repository_name":"test-for-fake-project",
    "repository_identifier":"510731046",
    "repository_owner_type":"Organization",
    "repository_owner_name":"Ring-tail-lemur",
    "repository_owner_id":"107110653",
    "repository_private":"false",
    "pull_request_opened_number":"218",
    "pull_request_open_branch":"gggggggg",
    "pull_request_close_branch":"hahahahahaahahaha",
    "pull_request_open_time":"2022-08-09T11:44:45Z",
    "pull_request_user_id" :"123456",

    "EventProcessedUtcTime": "2022-08-02T07:44:15.8134619Z",
    "PartitionId": 1,
    "EventEnqueuedUtcTime": "2022-08-02T07:44:15.542Z"
}
*/
