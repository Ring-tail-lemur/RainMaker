const getter = require('../http-get/get-commits.js');
const send_module = require('../event-hub/send.js');

async function commitMain(context, commits_url, isPrivate, pull_request_remote_identifier){
    if(isPrivate == 'true'){
        //사용자 개인 token 받아오는 logic 필요
        const token = '자신의 토큰을 넣도록 하자. 우리는 척척척 스스로 어린이.';
        await getter.getCommitsWithToken(commits_url, token,pull_request_remote_identifier);
    }else{
        // context.log("commits_url : " + commits_url);
        await getter.getCommitsWithoutToken(context, commits_url,pull_request_remote_identifier);
    }
}

module.exports.commitMain = commitMain;
