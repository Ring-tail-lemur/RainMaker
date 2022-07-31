// context.log("now commitMain : " + stringCommitList);
// context.log("여기까진 끝남");
// const commitsCount = JSON.parse(stringCommitList).length;
// for(let commitObjIndex = 0; commitObjIndex < commitsCount; commitObjIndex++){
//     const eventHubCommitObject = await parsingCommit(parsedCommitList[commitObjIndex], pull_request_remote_identifier);
//     context.log(JSON.stringify(eventHubCommitObject));
//     send_module.sender(eventHubCommitObject);
// }
// return commitsCount;


async function parsingCommit(context, commitObj, parent_pull_request_remote_identifier){
    const eventHubCommitObj = new Object();
    eventHubCommitObj.source = 'github';
    eventHubCommitObj.parent_pull_request_remote_identifier = parent_pull_request_remote_identifier;
    eventHubCommitObj.commit_sha = JSON.stringify(commitObj.sha).replace(/['"]+/g, '');
    eventHubCommitObj.parent_commit_sha = JSON.stringify(commitObj.parents.sha).replace(/['"]+/g, '');
    eventHubCommitObj.commit_author_name = JSON.stringify(commitObj.commit.author.name);
    eventHubCommitObj.commit_author_email = JSON.stringify(commitObj.commit.author.email);
    eventHubCommitObj.commit_message = JSON.stringify(commitObj.message);

    context.log(JSON.stringify(eventHubCommitObj));
    return eventHubCommitObj;
}



module.exports.parsingCommit = parsingCommit;