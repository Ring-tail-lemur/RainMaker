const pullRequestCreateRepository = require('./pullRequestCreateRepository');

async function pullRequestLabeledMain(pool, eventObject, context) {

    //pull_request runtime_error_label_id 추가
    await pullRequestCreateRepository.updatePullRequestRuntimeErrorLabelId(pool, eventObject.pull_request_label_id, eventObject.pull_request_remote_identifier);
}

module.exports.pullRequestLabeledMain = pullRequestLabeledMain;
