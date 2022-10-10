const err_log_module = require('../utils/slackLogBot.js');
async function deleteMain(hookBody, cloudEventObj, context){
    try{
        cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
        cloudEventObj.ref_name = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    
        cloudEventObj.ref_repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
        cloudEventObj.ref_repository_isPrivate = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
        cloudEventObj.ref_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
        cloudEventObj.ref_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
        cloudEventObj.ref_delete_user_id = JSON.stringify(hookBody.sender.id).replace(/['"]+/g, '');
        if(cloudEventObj.ref_type == 'branch'){
            cloudEventObj.action = 'delete_branch';
        }else if(cloudEventObj.ref_type == 'tag'){
            cloudEventObj.action = 'delete_tag';
        }else{
            cloudEventObj.hookBody = 'notYet';
        }
        return cloudEventObj;
    }catch(e){
        err_log_module.log(e,"deleteMainModule.js");
    }

}
module.exports.deleteMain = deleteMain;