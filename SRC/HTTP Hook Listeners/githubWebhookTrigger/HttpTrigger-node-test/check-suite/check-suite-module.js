async function checkSuiteMain(context, hookBody, cloudEventObj){
    let resultObj = new Object();
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');

    cloudEventObj.check_suite_id = JSON.stringify(hookBody.check_suite.id);
    cloudEventObj.status = JSON.stringify(hookBody.check_suite.status);
    cloudEventObj.conclusion = JSON.stringify(hookBody.check_suite.conclusion);
    cloudEventObj.head_branch = JSON.stringify(hookBody.check_suite.head_branch);
    cloudEventObj.head_commit_id = JSON.stringify(hookBody.check_suite.head_commit.id);
    cloudEventObj.deployment_time = JSON.stringify(hookBody.check_suite.updated_at);
    
    return cloudEventObj;
}

module.exports.checkSuiteMain = checkSuiteMain;