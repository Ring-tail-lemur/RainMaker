async function pullRequestReviewMain(hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.pull_request = JSON.stringify(hookBody.pull_request.url);
    cloudEventObj.review = JSON.stringify(hookBody.review.url);
    if(cloudEventObj.action == 'edited'){
        cloudEventObj.changes = JSON.stringify(hookBody.changes).replace(/['"]+/g, '');
    }
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    return cloudEventObj;
}
module.exports.pullRequestReviewMain = pullRequestReviewMain;