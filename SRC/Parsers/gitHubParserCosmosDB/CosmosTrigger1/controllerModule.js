const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
const repositoryMainModule = require('./repository/repositoryMainModule.js');
const createMainModule = require('./create/createMainModule.js');
const commitMainModule = require('./commit/commitMainModule.js');
const pullRequestReviewMainModule = require('./review/pullRequestReviewMainModule');
// const pool = require('./ms-sql/msSQLPool');

async function controllerMain(eventObj, context, pool){

    
    const hook_event = eventObj.hook_event;

    context.log("EventObj :", eventObj);

    if(hook_event == 'pull_request'){
        await pullRequestMainModule.pullRequestMain(pool, eventObj, context);
        context.log("pull_request insert success");
    }else if(hook_event == 'commit'){
        await commitMainModule.commitMain(pool, eventObj, context);
        context.log("commit insert success");
    }else if(hook_event == 'repository'){
        await repositoryMainModule.repositoryMain(pool ,eventObj);
        context.log("repository insert success");
    }else if(hook_event == 'create'){
        await createMainModule.createMain(pool, eventObj, context);
        context.log("create(branch making) insert success");
    }else if(hook_event == 'pull_request_review') {
        await pullRequestReviewMainModule.pullRequestReviewMain(pool, eventObj);
        context.log("pull_request_review insert success");
    }

}

module.exports.controllerMain = controllerMain;