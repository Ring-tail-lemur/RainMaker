const getter = require('../http-get/get-commits.js');
const sendModule = require('../event-hub/send.js');
const parsingModule = require('./commit-parsing.js');

async function commitMain(context, commits_url, isPrivate, pull_request_remote_identifier){

    if(isPrivate == 'true'){
        //사용자 개인 token 받아오는 logic 필요
        const token = '자신의 토큰을 넣도록 하자. 우리는 척척척 스스로 어린이.';
        const commitList = await getter.getCommitsWithToken(context, commits_url, token,pull_request_remote_identifier);
        context.log("--------- commit main ---------\n"+commitList+"\n--------- commit main ---------\n");
        const commit_cnt = commitList.length;
        for(let i = 0; i < commit_cnt; i++){
            const resultObj = await parsingModule.parsingCommit(context,commitList[i],pull_request_remote_remote_remote);
            await sendModule.sender(resultObj,context);
        }
        return commit_cnt;
    }else{
        const commitList = await getter.getCommitsWithoutToken(context, commits_url,pull_request_remote_identifier);
        context.log("--------- commit main ---------\n"+commitList+"\n--------- commit main ---------\n");
        const commit_cnt = commitList.length;
        for(let i = 0; i < commit_cnt; i++){
            const resultObj = await parsingModule.parsingCommit(context,commitList[i],pull_request_remote_remote_remote);
            await sendModule.sender(resultObj,context);
        }
        return commit_cnt;
    }
}

module.exports.commitMain = commitMain;
