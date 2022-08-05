const pullRequestOpenModule = require('./pullRequestOpenModule.js');
const pullRequestCloseModule = require('./pullRequestClosedModule.js');

async function pullRequestMain(eventObject,context){
    if(eventObject.action == 'opened'){
        await pullRequestOpenModule.pullRequestOpenMain(eventObject,context);
    }else if(eventObject.action == 'closed'){
        await pullRequestCloseModule.pullRequestCloseMain(eventObject,context);
    }else{
        //TODO: handle syncronized and ects. 현재 opened/closed 외에는 개발 진행 X. do nothing.
    }
}
module.exports.pullRequestMain = pullRequestMain;