async function deleteMain(hookBody, cloudEventObj, context){
    cloudEventObj.ref = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
    cloudEventObj.action = (cloudEventObj.ref_type == 'branch' ? 'delete_branch' : 'delete_tag');
    cloudEventObj.pusher_type = JSON.stringify(hookBody.pusher_type).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');

    return cloudEventObj;
}
module.exports.deleteMain = deleteMain;