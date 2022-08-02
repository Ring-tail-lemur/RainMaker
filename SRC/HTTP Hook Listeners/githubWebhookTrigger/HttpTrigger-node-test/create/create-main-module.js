async function createMain(context, hookBody, cloudEventObj){
    try{
        cloudEventObj.action = 'create_ref';
        cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
        cloudEventObj.branch_name = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
        cloudEventObj.author_id = JSON.stringify(hookBody.sender).replace(/['"]+/g, '');
        try{
            cloudEventObj.organization_id = JSON.stringify(hookBody.organization.id).replace(/['"]+/g, '');
        }catch(e){
            context.log("not a organization repo");
        }
        return cloudEventObj;
    }catch(e){
        cloudEventObj.action = 'tag';
        return cloudEventObj;
    }
    

}

module.exports.createMain = createMain;