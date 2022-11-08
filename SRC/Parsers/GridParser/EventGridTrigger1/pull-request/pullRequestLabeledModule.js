const pullRequestCreateRepository = require('./pullRequestCreateRepository');
const {pullRequestMain} = require("./pullRequestMainModule");

function isRuntimeErrorIssueLabel(eventObject) {
    return eventObject.pull_request_label_name.includes('[RainMaker]runtime-error-');
}

async function pullRequestLabeledMain(pool, eventObject, context) {

    //pull_request runtime_error_label_id 추가
    if (isRuntimeErrorIssueLabel(eventObject)) {
        await pullRequestCreateRepository.updatePullRequestRuntimeErrorLabelId(pool, eventObject.pull_request_label_id, eventObject.pull_request_remote_identifier);
    }
}

module.exports.pullRequestLabeledMain = pullRequestLabeledMain;
