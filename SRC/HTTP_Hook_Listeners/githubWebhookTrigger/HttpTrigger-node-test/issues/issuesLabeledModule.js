async function issueLabledMain(hookBody,cloudEventObj, context) {
    const labels = hookBody.issue.labels;
    
}

module.exports.issueLabledMain = issueLabledMain;