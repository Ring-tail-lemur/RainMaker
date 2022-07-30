const { sender } = require("./event-hub/send.js");
const pull_requst_module = require("./pull-request/pull-request-main.js");
// test 12
// test for github action-PR mapping

module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');
    const cloudEventObj = new Object();
    const hookBody = req.body;
    const hookHeaders = req.headers;

    // .replace(/['"]+/g, '') <- double quote problem solve (e.g. "\"hi\"")
    cloudEventObj.hook_event = JSON.stringify(hookHeaders["X-GitHub-Event"]).replace(/['"]+/g, '');
    cloudEventObj.source = 'github'.replace(/['"]+/g, '');

    // 분기, pull_request || pull_request_review || fork || release || issue_comment || create(branch)
    // 상황에 따른 비동기 모듈 분리로 scaleable하게 갈 것.
    if(cloudEventObj.hook_event == 'pull_request'){
        context.log('pull_request event occur');
        var resultObj = await pull_requst_module.pull_request(context,hookBody,cloudEventObj);
        sender.sender(resultObj);
    }else if(cloudEventObj.hook_event == 'pull_request_review'){

    }else if(cloudEventObj.hook_event == 'pull_request_review_comment'){

    }

    context.res ={
        body : "실패"
    }
}




