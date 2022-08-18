async function issueClosedMain(hookBody, cloudEventObj, context){
    cloudEventObj.action_time = JSON.stringify(hookBody.issue.closed_at).replace(/['"]+/g, '');

    return cloudEventObj;
}
module.exports.issueClosedMain = issueClosedMain;