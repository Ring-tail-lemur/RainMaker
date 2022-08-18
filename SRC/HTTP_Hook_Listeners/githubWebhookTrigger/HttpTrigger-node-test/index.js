const send_module  = require("./event-hub/send.js");
const pull_request_module = require("./pull-request/pull-request-main.js");
const issueCommentModule = require("./issue-comment/issue-comment-module.js");
const pullRequestReviewModule = require("./pull-request-review/pull-request-review-module.js");
const pullRequestReviewCommentModule = require("./pull-request-review-comment/pull-request-review-comment-module.js");
const checkSuiteModule = require("./check-suite/check-suite-module.js");
const repositoryModule = require("./repository/repository-main-module.js");
const createModule = require('./create/create-main-module');
const issuesModule = require('./issues/issueMainModule.js');
const workflowRunModule = require('./workflow-run/workflowRunMainModule.js');
const releaseModule = require('./release/releaseMainModule.js');
const deleteModule = require('./delete/deleteMainModule.js');
const timeModule = require('./utils/getCurrentTimeModule.js');
module.exports = async function (context, req) {
    const cloudEventObj = new Object();
    const hookBody = req.body;
    const hookHeaders = req.headers;
// test 123232
    // .replace(/['"]+/g, '') <- double quote problem solve (e.g. "\"hi\"")
    cloudEventObj['X-GitHub-Delivery'] = JSON.stringify(hookHeaders['X-GitHub-Delivery']).replace(/['"]+/g, '');
    cloudEventObj.hook_event = JSON.stringify(hookHeaders['x-github-event']).replace(/['"]+/g, '');
    cloudEventObj.source = 'github';
    cloudEventObj.utc_time_in_listener = await timeModule.getCurrentTime();
// 
    // 분기, pull_request || pull_request_review || fork || release || issue_comment || create(branch)
    // 상황에 따른 비동기 모듈 분리로 scaleable하게 갈 것.
    if(cloudEventObj.hook_event == 'pull_request'){
        context.log('pull_request event occur');
        const resultObj = await pull_request_module.pullRequestMain(context, hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'pull_request_review'){
        context.log("pull_request_review!");
        const resultObj = await pullRequestReviewModule.pullRequestReviewMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'pull_request_review_comment'){
        context.log("pull_request_review_comment ocurred");
        const resultObj = await pullRequestReviewCommentModule.pullRequestReviewCommentMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'issue_comment'){
        context.log("issue_comment event occurred");
        const resultObj = await issueCommentModule.issueCommentMain(context,hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'check_suite'){
        context.log("check_suite event occurred");
        cloudEventObj.source = 'github-action';
        const resultObj = await checkSuiteModule.checkSuiteMain(context,hookBody,cloudEventObj);
        await send_module.sender(resultObj, context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'repository' ){
        context.log("repository event ocurred");
        const resultObj = await repositoryModule.repositoryMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else if(cloudEventObj.hook_event == 'create'){
        context.log("create branch/tag event ocurred.");
        const resultObj = await createModule.createMain(context, hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else if(cloudEventObj.hook_event == 'issues'){
        // 레포지토리 안에서 런타임 issue를 남기는 경우, action : labeled를 봐야한다.
        // project 안에서 런타임 issue를 남기는 경우, action : edited를 봐야한다.
        context.log("create issue");
        const resultObj = await issuesModule.issueMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else if(cloudEventObj.hook_event == 'workflow_run'){
        const resultObj = await workflowRunModule.workflowRunMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else if(cloudEventObj.hook_event == 'release'){
        const resultObj = await releaseModule.releaseMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else if(cloudEventObj.hook_event == 'delete'){
        const resultObj = await deleteModule.deleteMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }else{
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }

    context.res ={
        body : JSON.stringify(cloudEventObj)
    }
}




