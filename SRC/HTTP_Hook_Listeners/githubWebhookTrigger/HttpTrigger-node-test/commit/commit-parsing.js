const timeModule = require('../utils/getCurrentTimeModule.js');

async function parsingCommit(context, commitObj, parent_pull_request_remote_identifier){
    const eventHubCommitObj = new Object();
    eventHubCommitObj.source = 'github';
    eventHubCommitObj.hook_event = 'commit';
    eventHubCommitObj.action = 'commit';
    eventHubCommitObj.event_triggered_time = (await timeModule.getCurrentTime()).replace(/['"]+/g, '');
    eventHubCommitObj.parent_pull_request_remote_identifier = parent_pull_request_remote_identifier;
    eventHubCommitObj.commit_sha = JSON.stringify(commitObj.sha).replace(/['"]+/g, '');
    eventHubCommitObj.parent_commit_sha = JSON.stringify(commitObj.parents.sha);
    eventHubCommitObj.commit_author_name = JSON.stringify(commitObj.commit.author.name).replace(/['"]+/g, '');
    eventHubCommitObj.commit_author_email = JSON.stringify(commitObj.commit.author.email).replace(/['"]+/g, '');
    eventHubCommitObj.commit_message = JSON.stringify(commitObj.commit.message).replace(/['"]+/g, '');
    eventHubCommitObj.commit_time = JSON.stringify(commitObj.commit.committer.date).replace(/['"]+/g, '');
    eventHubCommitObj.commit_author_id = JSON.stringify(commitObj.author.id).replace(/['"]+/g, '');

    return eventHubCommitObj;
}

module.exports.parsingCommit = parsingCommit;