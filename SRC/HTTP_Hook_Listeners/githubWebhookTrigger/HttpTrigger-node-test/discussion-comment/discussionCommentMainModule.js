async function discussionCommentMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.comment_html_url = JSON.stringify(hookBody.comment.html_url).replace(/['"]+/g, '');
    cloudEventObj.discussion_html_url = JSON.stringify(hookBody.discussion.html_url).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');

    return cloudEventObj;
}

module.exports.discussionCommentMain = discussionCommentMain;