const labellingModule = require('./issueLabeledModule.js');
async function issueEditedMain(hookBody, cloudEventObj, context){
    cloudEventObj.action_time = JSON.stringify(hookBody.issue.updated_at).replace(/['"]+/g, '');
    try{
        //label이 되어있으면 그냥 집어넣자~
        cloudEventObj.labelNameList = await labellingModule.issueLabeling(hookBody, context);
        return cloudEventObj;
    }catch(e){
        return cloudEventObj;
    }
}

module.exports.issueEditedMain = issueEditedMain;