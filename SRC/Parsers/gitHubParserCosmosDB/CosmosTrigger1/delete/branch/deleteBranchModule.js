const deleteBranchRepository = require("./deleteBranchRepository");

async function pullRequestCloseMain(pool, eventObject, context) {

    await deleteBranchRepository.updateBranchByRepoIdAndUserId(pool, context, eventObject.X_GitHub_Delivery,eventObject.action, eventObject.pull_request_closed_time, eventObject.pull_request_remote_identifier, eventObject.pull_request_user_id);

}

module.exports.pullRequestCloseMain = pullRequestCloseMain;