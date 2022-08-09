const pullRequestOpenModule = require('./pullRequestOpenModule.js');
const pullRequestClosedModule = require('./pullRequestClosedModule');

async function pullRequestMain(eventObject,context){
    if(eventObject.action == 'opened'){
        pullRequestOpenModule.pullRequestOpenMain();
    }else if(eventObject.action == 'closed'){
        pullRequestClosedModule.pullRequestCloseMain();
    }else{
        //todo: handle syncronized and ects. 현재 opened/closed 외에는 개발 진행 X. do nothing.
    }
}
module.exports.pullRequestMain = pullRequestMain;