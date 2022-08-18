async function createMain(context, hookBody, cloudEventObj){
    cloudEventObj.ref = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
    cloudEventObj.action = (ref_type == 'branch'? 'create_branch' : 'create_tag');
    cloudEventObj.master_branch = JSON.stringify(hookBody.master_branch).replace(/['"]+/g, '');
    cloudEventObj.description = JSON.stringify(hookBody.description).replace(/['"]+/g, '');
    cloudEventObj.pusher_type = JSON.stringify(hookBody.pusher_type).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');

    return cloudEventObj;
}

module.exports.createMain = createMain;