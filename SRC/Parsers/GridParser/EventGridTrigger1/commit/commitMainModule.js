const commitCreateRepository = require('./commitCreateRepository.js');
const errLogModule = require('../utils/slackLogBot.js');

async function commitMain(pool, eventObject, context){
    //commit 에 대한 것. 내가 만든 것이므로 언젠가는 바뀔 수도 있다. action마저 commit으로, 분기될만한 것이 없음.
    //main에서 그냥 commits entity 생성 및 삽입해도 무방함

    //commit entity 생성 및 삽입

    //pull_request_commit_table 생성 및 삽입
    try {
        await commitCreateRepository.insertCommitByUserId(pool, eventObject.commit_sha, eventObject.commit_author_id, eventObject.commit_message, eventObject.commit_time, context);
    } catch (e) {
        errLogModule.log(e, "commitMainModule.js // commitMain");
        context.log(e);
    }
    await commitCreateRepository.insertPullRequestCommitTableByPullRequestIdAndCommitId(pool, eventObject.parent_pull_request_remote_identifier, eventObject.commit_sha, false);
}

module.exports.commitMain = commitMain;

/*
commit 이벤트 sample
{


    "source":"github",
    "hook_event":"commit",
    "action":"commit",
    "parent_pull_request_remote_identifier":"1021251621",
    "commit_sha":"612d31778aa006d1338f0ca2daec27e9171e0980",
    "commit_author_name":"jhbaik1501",
    "commit_author_email":"81180977+jhbaik1501@users.noreply.github.com",
    "commit_message":"테스트 메시지요",
    "commit_time":"2022-08-09T09:11:45Z",
    "commit_author_id":"81180977",

}
*/