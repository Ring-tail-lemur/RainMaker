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
const checkRunModule = require('./check-run/checkRunMainModule.js');
const codeScanningAlertModule = require('./code-scanning-alert/codeScanningAlertMainModule.js');
const commitCommentModule = require('./commit-comment/commitCommentMainModule.js');
const deployKeyModule = require('./deploy-key/deployKeyMainModule.js');
const deploymentModule = require('./deployment/deploymentMainModule.js');
const deploymentStatusModule = require('./deployment/deploymentStatusMainModule.js');
const discussionModule = require('./discussion/discussionMainModule.js');
const discussionCommentModule = require('./discussion-comment/discussionCommentMainModule.js');
const forkModule = require('./fork/forkMainModule.js');
const githubAppAuthorizationModule = require('./github-app-authorization/githubAppAuthorizationMainModule.js');
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

    //풀리
    if(cloudEventObj.hook_event == 'pull_request'){
        const resultObj = await pull_request_module.pullRequestMain(context, hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
        //리뷰
    }else if(cloudEventObj.hook_event == 'pull_request_review'){
        const resultObj = await pullRequestReviewModule.pullRequestReviewMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
        // 리뷰 안의 코멘트
    }else if(cloudEventObj.hook_event == 'pull_request_review_comment'){
        const resultObj = await pullRequestReviewCommentModule.pullRequestReviewCommentMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
        // 이슈 댓글
    }else if(cloudEventObj.hook_event == 'issue_comment'){
        const resultObj = await issueCommentModule.issueCommentMain(context,hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
        // 체크쉿
    }else if(cloudEventObj.hook_event == 'check_suite'){
        context.log("check_suite event occurred");
        cloudEventObj.source = 'github';
        const resultObj = await checkSuiteModule.checkSuiteMain(context,hookBody,cloudEventObj);
        await send_module.sender(resultObj, context);
        context.res ={
            body : JSON.stringify(resultObj)
        }
            //레포지토리
    }else if(cloudEventObj.hook_event == 'repository' ){
        const resultObj = await repositoryModule.repositoryMain(hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
        //태그, 브랜치
    }else if(cloudEventObj.hook_event == 'create'){
        const resultObj = await createModule.createMain(context, hookBody,cloudEventObj);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
        // 이슈
    }else if(cloudEventObj.hook_event == 'issues'){
        // 레포지토리 안에서 런타임 issue를 남기는 경우, action : labeled를 봐야한다.
        // project 안에서 런타임 issue를 남기는 경우, action : edited를 봐야한다.
        context.log("create issue");
        const resultObj = await issuesModule.issueMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
        // 워크플로우런
    }else if(cloudEventObj.hook_event == 'workflow_run'){
        const resultObj = await workflowRunModule.workflowRunMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
        //릴리즈
    }else if(cloudEventObj.hook_event == 'release'){
        const resultObj = await releaseModule.releaseMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
        //삭제(브랜치/태그)
    }else if(cloudEventObj.hook_event == 'delete'){
        const resultObj = await deleteModule.deleteMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_evnet == 'check_run'){
        const resultObj = await checkRunModule.checkRunMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'code_scanning_alert'){
        const resultObj = await codeScanningAlertModule.codeScanningAlertMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'commit_comment'){
        const resultObj = await commitCommentModule.commitCommentMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.action == 'deploy_key'){
        const resultObj = await deployKeyModule.deployKeyMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'deployment'){
        const resultObj = await deploymentModule.deploymentMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'deployment_status'){
        const resultObj = await deploymentStatusModule.deploymentStatusMain(hookBody,cloudEventObj,context).replace(/['"]+/g, '');
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'discussion'){
        const resultObj = await discussionModule.discussionMain(hookBody,cloudEventObj,context).replace(/['"]+/g, '');
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'discussion_comment'){
        const resultObj = await discussionCommentModule.discussionCommentMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'fork'){
        const resultObj = await forkModule.forkMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else if(cloudEventObj.hook_event == 'github_app_authorization'){
        const resultObj = await githubAppAuthorizationModule.githubAppAuthorizationMain(hookBody,cloudEventObj,context);
        await send_module.sender(resultObj,context);
        context.res = {
            body : JSON.stringify(resultObj)
        }
    }else{
        context.res = {
            body : JSON.stringify(cloudEventObj)
        }
    }
    //TODO: gollum
    //TODO: installation // installation_repositories // label // marketplace_purchase // member // membership //merge_group // meta // milestone // organization // org_block
    //TODO: package // page_build // ping // project // project_card //project_column // projects_v2_item // public // pull_request_review_thread // push // repository_dispatch // repository_import // repository_vulnerability_alert
    //TODO: security_advisory // sponsership // star // status // team // team_add // watch // workflow_dispatch // workflow_job
    context.res ={
        body : JSON.stringify(cloudEventObj)
    }
}




