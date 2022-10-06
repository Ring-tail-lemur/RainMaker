
const labellingModule = require('./issueLabeledModule.js');
async function issueOpenedMain(hookBody, cloudEventObj, context){
    cloudEventObj.action_time = JSON.stringify(hookBody.issue.created_at).replace(/['"]+/g, '');
    try{
        cloudEventObj.labelNameList = await labellingModule.issueLabeling(hookBody, context);
        return cloudEventObj;
    }catch(e){
        return cloudEventObj;
    }
}

module.exports.issueOpenedMain = issueOpenedMain;