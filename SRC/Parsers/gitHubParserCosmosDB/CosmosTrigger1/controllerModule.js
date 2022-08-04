const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
const repositoryMainModule = require('./repository/repositoryMainModule.js');



async function controllerMain(eventObj, context){

    const hook_event = eventObj.hook_event;

    if(hook_event == 'pull_request'){
        await pullRequestMainModule.pullRequestMain(eventObj);
    }else if(hook_event == 'commit'){

    }else if(hook_event == 'repository'){
        await repositoryMainModule.repositoryMain(eventObj);
    }
}

module.exports.controllerMain = controllerMain;