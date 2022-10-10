const getPullRequestModule = require('./getPullRequestModule.js');
const msSQLModule = require('../ms-sql/msSQLModule.js');
const err_log_module = require('../utils/slackLogBot.js');
async function issueCommentMain(context, hookBody, cloudEventObj){
    try{
        cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
        cloudEventObj.issue_comment_remote_id = JSON.stringify(hookBody.issue.id);
        cloudEventObj.issue_comment_repository_number = JSON.stringify(hookBody.issue.number);
        cloudEventObj.git_user_remote_id = JSON.stringify(hookBody.sender.id);
        cloudEventObj.event_time = JSON.stringify(hookBody.issue.created_at).replace(/['"]+/g, '');
        cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id);
        cloudEventObj.isPrivate = JSON.stringify(hookBody.repository.private);
        context.log("[issue-comment-module.js] I'll get accessToken By RepositoryId : " + cloudEventObj.repository_id);
        const accessToken = await msSQLModule.getTokenByRepositoryId(cloudEventObj.repository_id, context);
        context.log("[issue-comment-module.js] accessToken By RepositoryId : " + accessToken);
        try{
            const pull_request_url = JSON.stringify(hookBody.issue.pull_request.url).replace(/['"]+/g, '');
            cloudEventObj.pull_request_id = await getPullRequestModule.getPullRequestMain(pull_request_url,cloudEventObj.isPrivate,context, accessToken);
        }catch(e){
            console.log(e);
        }
        return cloudEventObj;
    }catch(e){ 
        err_log_module.log(e, "issue-comment-module.js");
    }
}

module.exports.issueCommentMain = issueCommentMain;