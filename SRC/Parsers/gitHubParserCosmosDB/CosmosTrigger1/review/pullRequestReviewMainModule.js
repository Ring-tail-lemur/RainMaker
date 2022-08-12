const pullRequestReviewSubmitModule = require('./pullRequestReviewSubmitModule');

async function pullRequestReviewMain(eventObject, context) {
    //pull request reveiew는 pull_request_reivew__comment와 동급으로 간주하기로 함.
    await pullRequestReviewSubmitModule.pullRequestSubmitModule(eventObject);
}

module.exports.pullRequestReviewMain = pullRequestReviewMain;