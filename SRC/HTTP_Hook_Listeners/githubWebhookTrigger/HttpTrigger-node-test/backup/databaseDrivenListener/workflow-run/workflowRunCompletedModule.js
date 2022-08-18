async function workflowRunCompleteMain(hookBody, cloudEventObj, context){
    const workflow_name = JSON.stringify(hookBody.workflow_run.name).replace(/['"]+/g, '');
    if(workflow_name.indexOf('[Deploy]') != -1){
        cloudEventObj.conclusion = JSON.stringify(hookBody.workflow_run.conclusion).replace(/['"]+/g, '');
        cloudEventObj.event_time = JSON.stringify(hookBody.workflow_run.updated_at).replace(/['"]+/g, '');
        return cloudEventObj;    
    }else{
        cloudEventObj.source = 'notYet';
        return cloudEventObj;
    }

}

module.exports.workflowRunCompleteMain = workflowRunCompleteMain;