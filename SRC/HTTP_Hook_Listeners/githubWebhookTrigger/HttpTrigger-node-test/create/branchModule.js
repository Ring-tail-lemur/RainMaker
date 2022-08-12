async function branchMain(hookBody, cloudEventObj, context){
    cloudEventObj.branch_name = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.author_id = JSON.stringify(hookBody.sender.id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.repository_full_name = JSON.stringify(hookBody.repository.full_name).replace(/['"]+/g, '');
    cloudEventObj.organization_id = JSON.stringify(hookBody.organization.id).replace(/['"]+/g, '');
    return cloudEventObj;
}
module.exports.branchMain = branchMain;