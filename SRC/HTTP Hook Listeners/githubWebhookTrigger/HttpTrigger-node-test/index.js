const send_module  = require("./event-hub/send.js");
const pull_request_module = require("./pull-request/pull-request-main.js");
const issueCommentModule = require("./issue-comment/issue-comment-module.js");
const pullRequestReviewModule = require("./pull-request-review/pull-request-review-module.js");
const pullRequestReviewCommentModule = require("./pull-request-review-comment/pull-request-review-comment-module.js");
const checkSuiteModule = require("./check-suite/check-suite-module.js");

module.exports = async function (context, req) {
    const cloudEventObj = new Object();
    const hookBody = req.body;
    const hookHeaders = req.headers;


    // .replace(/['"]+/g, '') <- double quote problem solve (e.g. "\"hi\"")
    context.log("now : " + JSON.stringify(hookHeaders['x-github-event']));
    cloudEventObj.hook_event = JSON.stringify(hookHeaders['x-github-event']).replace(/['"]+/g, '');
    cloudEventObj.source = 'github';

    // 분기, pull_request || pull_request_review || fork || release || issue_comment || create(branch)
    // 상황에 따른 비동기 모듈 분리로 scaleable하게 갈 것.
    if(cloudEventObj.hook_event == 'pull_request'){
        context.log('pull_request event occur');
        const resultObj = await pull_request_module.pullRequestMain(context,hookBody,cloudEventObj);
        send_module.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'pull_request_review'){
        context.log("pull_request_review!");
        const resultObj = await pullRequestReviewModule.pullRequestReviewMain(context,hookBody,cloudEventObj);
        send_module.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'pull_request_review_comment'){
        context.log("pull_request_review_comment ocurred");
        const resultObj = await pullRequestReviewCommentModule.pullRequestReviewCommentMain(context,hookBody,cloudEventObj);
        send_module.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'issue_comment'){
        context.log("issue_comment event occurred");
        const resultObj = await issueCommentModule.issueCommentMain(context,hookBody,cloudEventObj);
        send_module.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'check_suite'){
        context.log("check_suite event occurred");
        const resultObj = await checkSuiteModule.checkSuiteMain(context,hookBody,cloudEventObj);
        send_module.sender(resultObj);
        context.res ={
            body : JSON.stringify(resultObj)
        }
    }else{
        context.res = {
            body : JSON.stringify(hookHeaders)
        }
    }

    context.res ={
        body : JSON.stringify(cloudEventObj)
    }
}




