const deleteBranchModule = require("./branch/deleteBranchModule");

async function deleteMain(pool, eventObject, context) {

    if(eventObject.ref_type == 'branch') {
        await deleteBranchModule.pullRequestCloseMain(pool, eventObject, context);
    }
}

module.exports.deleteMain = deleteMain;