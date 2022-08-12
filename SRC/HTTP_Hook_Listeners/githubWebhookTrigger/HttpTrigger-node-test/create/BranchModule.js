async function branchMain(hookBody, cloudEventObj, context){
    cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
    cloudEventObj.branch_name = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.author_id = JSON.stringify(hookBody.sender.id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_full_name = JSON.stringify(hookBody.repository.full_name).replace(/['"]+/g, '');
    try{
        cloudEventObj.organization_id = JSON.stringify(hookBody.organization.id).replace(/['"]+/g, '');
    }catch(e){
        context.log("not a organization repo");
    }
    return cloudEventObj;
}
module.exports.branchMain = branchMain;