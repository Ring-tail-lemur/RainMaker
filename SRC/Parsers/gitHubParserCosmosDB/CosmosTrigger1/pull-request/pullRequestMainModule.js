const pullRequestOpenModule = require('./pullRequestOpenModule.js');
const pullRequestClosedModule = require('./pullRequestClosedModule');

async function pullRequestMain(eventObject,context){
    if(eventObject.action == 'opened'){

        await pullRequestOpenModule.pullRequestOpenMain(eventObject, context);
    }else if(eventObject.action == 'closed'){
        await pullRequestClosedModule.pullRequestCloseMain(eventObject, context);
    }else{
        // todo: handle syncronized and ects. 현재 opened/closed 외에는 개발 진행 X. do nothing.
    }
}
module.exports.pullRequestMain = pullRequestMain;