async function tagMain(hookBody, cloudEventObj, context){
    cloudEventObj.ref_name = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
    cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
    cloudEventObj.owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
    cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.tagMain = tagMain;