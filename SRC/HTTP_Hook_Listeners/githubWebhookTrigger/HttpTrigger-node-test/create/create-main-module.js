const branchModule = require('./branchModule.js');
const tagModule = require('./tagModule.js');
const err_log_module = require('../utils/slackLogBot.js');
async function createMain(context, hookBody, cloudEventObj){
    try{
        cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
        if(cloudEventObj.ref_type == 'branch'){
            cloudEventObj.action = 'created_branch';
            return await branchModule.branchMain(hookBody, cloudEventObj, context);
        }else if(cloudEventObj.ref_type == 'tag'){
            cloudEventObj.action = 'created_tag';
            return await tagModule.tagMain(hookBody, cloudEventObj, context);
        }else{
            return new Object();
        }
    }catch(e){
        err_log_module.log(e, "create-main-module.js");
    }
}

module.exports.createMain = createMain;