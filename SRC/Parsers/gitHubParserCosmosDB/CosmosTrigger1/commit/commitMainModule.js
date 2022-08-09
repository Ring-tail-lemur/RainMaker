const commitCreateRepository = require('./commitCreateRepository.js');

async function commitMain(eventObject, context){
    //commit 에 대한 것. 내가 만든 것이므로 언젠가는 바뀔 수도 있다. action마저 commit으로, 분기될만한 것이 없음.
    //main에서 그냥 commits entity 생성 및 삽입해도 무방함

    //commit entity 생성 및 삽입

    //pull_request_commit_table 생성 및 삽입
    await commitCreateRepository.insertCommitByUserId(eventObject.commit_sha, eventObject.commit_author_id, eventObject.commit_message);
}

module.exports.commitMain = commitMain;

/*
commit 이벤트 sample
{


    "source":"github",
    "hook_event":"commit",
    "action":"commit",
    "parent_pull_request_remote_identifier":"1021192604",
    "commit_sha":"dc01869a2f1c3577bbb88311a1f8e680c640ecdd",
    "commit_author_name":"jhbaik1501",
    "commit_author_email":"81180977+jhbaik1501@users.noreply.github.com",
    "commit_message":"\"Merge branch 'main' into hahahahahaahahaha\"",
    "commit_author_id":"81180977"

}
*/