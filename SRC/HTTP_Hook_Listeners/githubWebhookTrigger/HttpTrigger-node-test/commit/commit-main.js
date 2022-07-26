const getter = require('../http-get/get-commits.js');
const sendModule = require('../event-hub/send.js');
const parsingModule = require('./commit-parsing.js');
const msSqlModule = require('../ms-sql/msSQLModule.js');
const err_log_module = require('../utils/slackLogBot.js');
async function commitMain(context, commits_url, isPrivate, pull_request_remote_identifier,repositoryId){

    try{
        if(isPrivate == 'true'){
            //사용자 개인 token 받아오는 logic 필요
            context.log("[commit-main.js] I'll get accessToken By RepositoryId : " + repositoryId);
            const token = await msSqlModule.getTokenByRepositoryId(repositoryId, context);
            context.log("[commit-main.js] accessToken By RepositoryId : " + token);
            context.log("github Access Token : "+token);
            const commitList = await getter.getCommitsWithToken(context, commits_url, token,pull_request_remote_identifier);
            // context.log("--------- commit main ---------\n"+commitList+"\n--------- commit main ---------\n");
            const commit_cnt = commitList.length;
            for(let i = 0; i < commit_cnt; i++){
                const resultObj = await parsingModule.parsingCommit(context,commitList[i],pull_request_remote_identifier);
                await sendModule.sender(resultObj,context);
            }
            return commit_cnt;
        }else{
            const commitList = await getter.getCommitsWithoutToken(context, commits_url,pull_request_remote_identifier);
            
            const commit_cnt = commitList.length;
            for(let i = 0; i < commit_cnt; i++){
                const resultObj = await parsingModule.parsingCommit(context,commitList[i],pull_request_remote_identifier);
                await sendModule.sender(resultObj,context);
            }
            return commit_cnt;
        }
    }catch(e){
        err_log_module.log(e, "Commit-main.js");
    }
    
}

module.exports.commitMain = commitMain;
