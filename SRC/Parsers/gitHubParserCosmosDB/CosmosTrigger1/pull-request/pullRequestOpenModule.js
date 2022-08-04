
async function pullRequestOpenMain(eventObject, context) {
     
    //pull_request entity 생성 및 삽입
    let pullRequestEntity = new Object();
    pullRequestEntity.remote_identifier = NUMBER(eventObject.pull_request_remote_identifier);
    pullRequestEntity.pull_request_number = NUMBER(eventObject.pull_request_remote_identifier);
    

    //pull_request_direction entity 생성 및 삽입

    //pull_request_event entity 생성 및 삽입
}

module.exports.pullRequestOpenMain = pullRequestOpenMain;

/*
pullRequestOpen 이벤트 sample
{
    "hook_event": "pull_request",
    "source": "github",
    "action": "opened",
    "pull_request_remote_identifier": "1014736314",
    "repository_name": "test-for-fake-project",
    "repository_identifier": "510731046", 
    "repository_owner_type": "Organization", 
    "repository_owner_name": "Ring-tail-lemur",
    "repository_owner_id": "107110653", 
    "repository_private": "false",
    "pull_request_opened_number": "184",
    "pull_request_open_branch": "test",
    "pull_request_open_time": "2022-08-02T07:44:13Z",
    "EventProcessedUtcTime": "2022-08-02T07:44:15.8134619Z",
    "PartitionId": 1,
    "EventEnqueuedUtcTime": "2022-08-02T07:44:15.542Z"
}
*/