const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
const repositoryMainModule = require('./repository/repositoryMainModule.js');
const createMainModule = require('./create/createMainModule.js');
const commitMainModule = require('./commit/commitMainModule.js');
const pullRequestReviewMainModule = require('./review/pullRequestReviewMainModule');
const deduplicationRepository = require('./deduplicate/deduplicationRepository')
const releaseMainModule = require('./release/releaseMainModule');
const labelMainModule = require("./label/labelMainModule");
const issueMainModule = require('./issue/issueMainModule');
const deleteMainModule = require("./delete/deleteMainModule");
const err_log_module = require("./utils/slackLogBot.js");

// const pool = require('./ms-sql/msSQLPool');

async function controllerMain(eventObj, context, pool){

    const hook_event = eventObj.hook_event;

    context.log(hook_event);

    try {
        if (hook_event == 'pull_request') {
            await pullRequestMainModule.pullRequestMain(pool, eventObj, context);
            context.log("pull_request insert ");
        } else if (hook_event == 'commit') {
            await commitMainModule.commitMain(pool, eventObj, context);
            context.log("commit insert");
        } else if (hook_event == 'repository') {
            await repositoryMainModule.repositoryMain(pool, eventObj);
            context.log("repository insert");
        } else if (hook_event == 'create') {
            await createMainModule.createMain(pool, eventObj, context);
            context.log("create(branch making) insert ");
        } else if (hook_event == 'delete') {
            await deleteMainModule.deleteMain(pool, eventObj, context);
            context.log("delete(branch closed) insert ");
        } else if (hook_event == 'pull_request_review') {
            await pullRequestReviewMainModule.pullRequestReviewMain(pool, eventObj, context);
            context.log("pull_request_review insert ");
        } else if (hook_event == 'release') {
            await releaseMainModule.releaseMain(pool, eventObj, context);
            context.log("release insert ");
        } else if (hook_event == 'label') {
            await labelMainModule.labelMain(pool, eventObj, context);
            context.log("label insert(or delete or edit) ");
        } else if (hook_event == 'issues') {
            await issueMainModule.issueMain(pool, eventObj, context);
            context.log("issue insert(or delete or edit) ");
        }

        // await deduplicationRepository.insertDeduplication(pool, eventObj.id);
    } catch (e) {
        err_log_module.log(e, "main.js");
        console.log(e);
    } finally {

    }

}

module.exports.controllerMain = controllerMain;