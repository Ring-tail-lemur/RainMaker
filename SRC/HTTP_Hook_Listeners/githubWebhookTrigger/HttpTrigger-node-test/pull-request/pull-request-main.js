
const open_module = require('./open.js');
const close_module = require('./close.js');
const err_log_module = require('../utils/slackLogBot.js');
async function pullRequestMain(context, hookBody, cloudEventObj){
    let resultObj = new Object();
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.pull_request_remote_identifier = JSON.stringify(hookBody.pull_request.id).replace(/['"]]+/g, '');
    cloudEventObj.repository_name = JSON.stringify(hookBody.repository.name).replace(/['"]+/g, ''); 
    cloudEventObj.repository_identifier = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_name = JSON.stringify(hookBody.repository.owner.login).replace(/['"]+/g, '');
    cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
    cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    try{
        if(cloudEventObj.action == 'opened'){
            resultObj = await open_module.pullRequestOpen(hookBody,cloudEventObj);
        }else if(cloudEventObj.action == 'closed'){
            resultObj = await close_module.pullRequestClose(context, hookBody,cloudEventObj);
        }else{
            context.log("action : "+ cloudEventObj.action + " event occurred, not yet develop perfectly.");
            resultObj = cloudEventObj;
        }       
        return resultObj;
    }catch(e){
        err_log_module.log(e,`Pull_Request - ${cloudEventObj.action}`);
    }finally{
        return resultObj;
    }
}

module.exports.pullRequestMain = pullRequestMain;