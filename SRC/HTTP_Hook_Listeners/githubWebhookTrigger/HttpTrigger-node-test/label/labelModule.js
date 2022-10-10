const err_log_module = require('../utils/slackLogBot.js');

async function labelMain(hookBody, cloudEventObj, context){
    try{
        cloudEventObj.label = JSON.stringify(hookBody.label.name).replace(/['"]+/g, '');
        cloudEventObj.issue_label_id = JSON.stringify(hookBody.label.id).replace(/['"]+/g, '');
        cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
        cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    
        return cloudEventObj;
    }catch(e){
        err_log_module.log(e, "labelModule.js");
    }
}

module.exports.labelMain = labelMain;