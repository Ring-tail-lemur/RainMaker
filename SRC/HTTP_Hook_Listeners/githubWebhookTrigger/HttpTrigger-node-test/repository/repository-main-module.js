const err_log_module = require('../utils/slackLogBot.js');
async function repositoryMain(hookBody, cloudEventObj){
    try{
        cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
        if(cloudEventObj.action == 'created'){
            cloudEventObj.repository_remote_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
            cloudEventObj.repository_name = JSON.stringify(hookBody.repository.name).replace(/['"]+/g, '');
            cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
            cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
            return cloudEventObj;
        }else{
            cloudEventObj.source = 'not yet developed';
            return cloudEventObj;
        }
    }catch(e){
        err_log_module.log(e, "repository-main-module.js");
    }
    
    
}

module.exports.repositoryMain = repositoryMain;