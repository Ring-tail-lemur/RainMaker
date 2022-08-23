async function labelMain(hookBody, cloudEventObj, context){

    cloudEventObj.label = JSON.stringify(hookBody.label.name).replace(/['"]+/g, '');
    cloudEventObj.issue_label_id = JSON.stringify(hookBody.label.id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    if(cloudEventObj.action == 'created'){
        cloudEventObj.action = 'create_label';
    }else if(cloudEventObj.action == 'deleted'){
        cloudEventObj.action = 'delete_label';
    }else{
        cloudEventObj.hookBody = 'notYet';
    }
    return cloudEventObj;

}

module.exports.labelMain = labelMain;