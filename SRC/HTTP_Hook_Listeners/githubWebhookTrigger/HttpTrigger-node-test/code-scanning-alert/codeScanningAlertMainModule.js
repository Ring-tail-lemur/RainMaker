async function codeScanningAlertMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.alert = JSON.stringify(hookBody.alert.url).replace(/['"]+/g, '');
    cloudEventObj.ref = JSON.stringify(hookBody.ref).replace(/['"]+/g, '');
    cloudEventObj.commit_oid = JSON.stringify(hookBody.commit_oid).replace(/['"]+/g, '');
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');
    return cloudEventObj;
}

module.exports.codeScanningAlertMain =codeScanningAlertMain;