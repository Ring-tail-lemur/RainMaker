async function issueCommentMain(context, hookBody, cloudEventObj){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    if(cloudEventObj.action == 'edited'){
        try{
            cloudEventObj.changes = JSON.stringify(hookBody.changes).replace(/['"]+/g, '');
        }catch(e){
            cloudEventObj.changes = JSON.stringify(hookBody.changes);
        }
    }
    cloudEventObj.issue = JSON.stringify(hookBody.issue.url).replace(/['"]+/g, '');
    cloudEventObj.comment = JSON.stringify(hookBody.comment.url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
}

module.exports.issueCommentMain = issueCommentMain;