const deleteBranchRepository = require("./deleteBranchRepository");

async function pullRequestCloseMain(pool, eventObject, context) {

    await deleteBranchRepository.updateBranchByRepoIdAndUserId(pool, eventObject.ref_name, eventObject.ref_repository_id, eventObject.ref_owner_id);
}

module.exports.pullRequestCloseMain = pullRequestCloseMain;