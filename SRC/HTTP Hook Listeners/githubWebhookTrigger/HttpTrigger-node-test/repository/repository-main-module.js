async function repositoryMain(context, hookBody, cloudEventObj){

    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    if(cloudEventObj.action == 'create'){
        cloudEventObj.repository_remote_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
        cloudEventObj.repository_name = JSON.stringify(hookBody.repository.name).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
        return cloudEventObj;
    }else{
        cloudEventObj.source = 'not yet developed';
        return cloudEventObj;
    }
    
    
}

module.exports.repositoryMain = repositoryMain;