const { sender } = require("./event-hub/send.js");
const pull_request_module = require("./pull-request/pull-request-main.js");
// test 12
// test for github action-PR mapping

module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');
    const cloudEventObj = new Object();
    const hookBody = req.body;
    const hookHeaders = req.headers;

    // .replace(/['"]+/g, '') <- double quote problem solve (e.g. "\"hi\"")
    context.log(JSON.stringify(hookHeaders['X-GitHub-Event']));
    cloudEventObj.hook_event = JSON.stringify(hookHeaders['X-GitHub-Event']);
    cloudEventObj.source = 'github';

    // 분기, pull_request || pull_request_review || fork || release || issue_comment || create(branch)
    // 상황에 따른 비동기 모듈 분리로 scaleable하게 갈 것.
    context.log()
    if(cloudEventObj.hook_event == 'pull_request'){
        context.log('pull_request event occur');
        const resultObj = await pull_request_module.pull_request(context,hookBody,cloudEventObj);
        sender.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'pull_request_review'){
        context.log("pull_request_review! not yet!");
    }else if(cloudEventObj.hook_event == 'pull_request_review_comment'){
        context.log("pull_request_review_comment not yet!");
    }else{
        context.res = {
            body : JSON.stringify(hookHeaders)
        }
    }

    context.res ={
        body : JSON.stringify(cloudEventObj)
    }
}




