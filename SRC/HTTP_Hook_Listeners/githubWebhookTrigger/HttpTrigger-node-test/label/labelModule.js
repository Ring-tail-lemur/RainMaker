async function labelMain(hookBody, cloudEventObj, context){

    cloudEventObj.label = JSON.stringify(hookBody.label.name).replace(/['"]+/g, '');
    cloudEventObj.issue_label_id = JSON.stringify(hookBody.label.id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');

    return cloudEventObj;
}

module.exports.labelMain = labelMain;