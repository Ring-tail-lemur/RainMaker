const issueLabeledModule = require('./issueLabeledModule.js');
const issueOpenedModule = require('./issueOpenedModule.js');
const issueEditedModule = require('./issueEdittedModule.js');
const issueClosedModule = require('./issueClosedModule.js');
async function issueMain(hookBody, cloudEventObj, context){
    cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    cloudEventObj.issue = JSON.stringify(hookBody.issue.url);
    cloudEventObj.changes = JSON.stringify(hookBody.changes.url).replace(/['"]+/g, '');
    if(cloudEventObj.action == 'edited'){
        cloudEventObj.changes = JSON.stringify(hookBody.changes).replace(/['"]+/g, '');
    }
    cloudEventObj.repository = JSON.stringify(hookBody.repository.url).replace(/['"]+/g, '');
    cloudEventObj.organization = JSON.stringify(hookBody.organization.url).replace(/['"]+/g, '');
    cloudEventObj.sender = JSON.stringify(hookBody.sender.url).replace(/['"]+/g, '');

    return cloudEventObj;
}

module.exports.issueMain = issueMain;