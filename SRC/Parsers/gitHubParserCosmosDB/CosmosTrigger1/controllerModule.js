const pullRequestMainModule = require('./pull-request/pullRequestMainModule.js');
async function controllerMain(eventObj, context){
    if(eventObj.hook_event.hook_event == 'pull_request'){
        await pullRequestMainModule.pullRequestMain(eventObj);
    }else if(eventObj.hook_event == 'commit'){

    }else{

    }
}