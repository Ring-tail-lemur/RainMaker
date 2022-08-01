async function checkSuiteMain(context, hookBody, cloudEventObj){
    let resultObj = new Object();
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');

    cloudEventObj.check_suite_id = JSON.stringify(hookBody.check_suite.id);
    cloudEventObj.status = JSON.stringify(hookBody.check_suite.status).replace(/['"]+/g, '');
    cloudEventObj.conclusion = JSON.stringify(hookBody.check_suite.conclusion).replace(/['"]+/g, '');
    cloudEventObj.head_branch = JSON.stringify(hookBody.check_suite.head_branch).replace(/['"]+/g, '');
    cloudEventObj.head_commit_id = JSON.stringify(hookBody.check_suite.head_commit.id).replace(/['"]+/g, '');
    cloudEventObj.deployment_time = JSON.stringify(hookBody.check_suite.updated_at).replace(/['"]+/g, '');
    
    const commit_url = JSON.stringify(hookBody.repository.commits_url).replace(/['"]+/g, '').replace('{/sha}','/') + cloudEventObj.head_commit_id;
    context.log(commit_url);
    

    return cloudEventObj;
}

module.exports.checkSuiteMain = checkSuiteMain;