const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
const repositoryMainModule = require('./repository/repositoryMainModule.js');
const createMainModule = require('./create/createMainModule.js');
const commitMainModule = require('./commit/commitMainModule.js');
const pullRequestReviewMainModule = require('./review/pullRequestReviewMainModule');

async function controllerMain(eventObj, context){

    
    const hook_event = eventObj.hook_event;

    if(hook_event == 'pull_request'){
        await pullRequestMainModule.pullRequestMain(eventObj);
    }else if(hook_event == 'commit'){
        await commitMainModule.commitMain(eventObj);
    }else if(hook_event == 'repository'){
        await repositoryMainModule.repositoryMain(eventObj);
    }else if(hook_event == 'create'){
        await createMainModule.createMain(eventObj);
    }else if(hook_event == 'pull_request_review') {
        await pullRequestReviewMainModule.pullRequestReviewMain(eventObj);
    }
}

module.exports.controllerMain = controllerMain;