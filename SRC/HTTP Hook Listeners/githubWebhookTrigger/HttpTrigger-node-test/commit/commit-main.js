const getter = require('../http-get/get-commits.js');
const send_module = require('../event-hub/send.js');

module.exports ={
    async commitMain(context, commits_url, isPrivate, pull_request_remote_identifier){
        if(isPrivate == 'true'){
            //사용자 개인 token 받아오는 logic 필요
            const token = '자신의 토큰을 넣도록 하자. 우리는 척척척 스스로 어린이.';
            const commits = await getter.getCommitsWithToken(commits_url, token);
            let parsedCommitList = JSON.parse(commits);
            const commitsCount = parsedCommitList.length;
            for(let commitObjIndex = 0; commitObjIndex < commitsCount; commitObjIndex++){
                const eventHubCommitObject = await parsingCommit(parsedCommits[commitObjIndex], pull_request_remote_identifier);
                send_module.sender(eventHubCommitObject);
            }
        }else{
            context.log("commits_url : " + commits_url);
            let parsedCommitList = await getter.getCommitsAnyToken(context, commits_url);
            context.log("commits Body : "+ JSON.stringify(parsedCommitList));
            const commitsCount = parsedCommitList.length;
            for(let commitObjIndex = 0; commitObjIndex < commitsCount; commitObjIndex++){
                const eventHubCommitObject = await parsingCommit(parsedCommits[commitObjIndex], pull_request_remote_identifier);
                context.log(JSON.stringify(eventHubCommitObject));
                send_module.sender(eventHubCommitObject);
            }
        }

    },

    async parsingCommit(context, commitObj, parent_pull_request_remote_identifier){
        const eventHubCommitObj = new Object();
        eventHubCommitObj.source = 'github';
        eventHubCommitObj.parent_pull_request_remote_identifier = parent_pull_request_remote_identifier;
        eventHubCommitObj.commit_sha = JSON.stringify(commitObj.sha).replace(/['"]+/g, '');
        eventHubCommitObj.parent_commit_sha = JSON.stringify(commitObj.parents.sha).replace(/['"]+/g, '');
        eventHubCommitObj.commit_author_name = JSON.stringify(commitObj.commit.author.name);
        eventHubCommitObj.commit_author_email = JSON.stringify(commitObj.commit.author.email);
        eventHubCommitObj.commit_message = JSON.stringify(commitObj.message);

        return eventHubCommitObj;
    }
}