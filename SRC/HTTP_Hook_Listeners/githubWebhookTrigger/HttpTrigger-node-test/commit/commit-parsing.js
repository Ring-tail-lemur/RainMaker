const sendModule = require('../event-hub/send.js');
const getIdFromNameModule = require('../http-get/from-name-get-id.js');

//test
async function parsingCommit(context, commitObj, parent_pull_request_remote_identifier){
    const eventHubCommitObj = new Object();
    eventHubCommitObj.source = 'github';
    eventHubCommitObj.hook_event = 'commit';
    eventHubCommitObj.action = 'commit';
    eventHubCommitObj.parent_pull_request_remote_identifier = parent_pull_request_remote_identifier;
    eventHubCommitObj.commit_sha = JSON.stringify(commitObj.sha).replace(/['"]+/g, '');
    eventHubCommitObj.parent_commit_sha = JSON.stringify(commitObj.parents.sha);
    eventHubCommitObj.commit_author_name = JSON.stringify(commitObj.commit.author.name).replace(/['"]+/g, '');
    eventHubCommitObj.commit_author_email = JSON.stringify(commitObj.commit.author.email).replace(/['"]+/g, '');
    eventHubCommitObj.commit_message = JSON.stringify(commitObj.message);
    eventHubCommitObj.commit_author_id = await getIdFromNameModule.getUserId(eventHubCommitObj.commit_author_name, context);

    return eventHubCommitObj;
}

module.exports.parsingCommit = parsingCommit;