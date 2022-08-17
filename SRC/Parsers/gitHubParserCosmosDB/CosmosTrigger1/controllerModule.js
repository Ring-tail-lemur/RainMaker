const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
const repositoryMainModule = require('./repository/repositoryMainModule.js');
const createMainModule = require('./create/createMainModule.js');
const commitMainModule = require('./commit/commitMainModule.js');
const pullRequestReviewMainModule = require('./review/pullRequestReviewMainModule');
const deduplicationRepository = require('./deduplicate/deduplicationRepository')
const releaseMainModule = require('./release/releaseMainModule');
// const pool = require('./ms-sql/msSQLPool');

async function controllerMain(eventObj, context, pool){

    // context.log("EventObj :", eventObj);

    // Deduplicate가 일어났다면 true를 반환.
    // const isDeduplicate = await deduplicationRepository.checkDeduplication(pool, eventObj.id);
    // if(isDeduplicate) {
    //     return;
    // }

    const hook_event = eventObj.hook_event;

    try {
        if (hook_event == 'pull_request') {
            await pullRequestMainModule.pullRequestMain(pool, eventObj, context);
            context.log("pull_request insert success");
        } else if (hook_event == 'commit') {
            await commitMainModule.commitMain(pool, eventObj, context);
            context.log("commit insert success");
        } else if (hook_event == 'repository') {
            await repositoryMainModule.repositoryMain(pool, eventObj);
            context.log("repository insert success");
        } else if (hook_event == 'create') {
            await createMainModule.createMain(pool, eventObj, context);
            context.log("create(branch making) insert success");
        } else if (hook_event == 'pull_request_review') {
            await pullRequestReviewMainModule.pullRequestReviewMain(pool, eventObj);
            context.log("pull_request_review insert success");
        } else if (hook_event == 'release') {
            await releaseMainModule.releaseMain(pool, eventObj, context);
            context.log("release insert success");
        }

        // await deduplicationRepository.insertDeduplication(pool, eventObj.id);
    } catch (e) {
        console.log(e);
    } finally {

    }

}

module.exports.controllerMain = controllerMain;