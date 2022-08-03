async function commitMain(eventObject, context){
    //commit 에 대한 것. 내가 만든 것이므로 언젠가는 바뀔 수도 있다. action마저 commit으로, 분기될만한 것이 없음.
    //main에서 그냥 commits entity 생성 및 삽입해도 무방함

    //commit entity 생성 및 삽입

    //pull_request_commit_table 생성 및 삽입
    
}

module.exports.commitMain = commitMain;

/*
commit 이벤트 sample
{
    "source": "github",
    "hook_event": "commit", 
    "action": "commit", 
    "parent_pull_request_remote_identifier": "1014736314", 
    "commit_sha": "b6ae0c9f05aeca8f6b1b2a1b429a43d74b58be46", 
    "commit_author_name": "vidigummy", 
    "commit_author_email": "vigigummy@gmail.com", 
    "commit_author_id": "33488236", 
    "EventProcessedUtcTime": "2022-08-02T07:44:25.6400787Z", 
    "PartitionId": 1, "EventEnqueuedUtcTime": "2022-08-02T07:44:25.596Z"
}
*/