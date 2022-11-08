const pullRequestOpenModule = require('./pullRequestOpenModule.js');
const pullRequestClosedModule = require('./pullRequestClosedModule');
const pullRequestLabeledModule = require('./pullRequestLabeledModule.js')

async function pullRequestMain(pool, eventObject,context){

    context.log("****************** flag 1 *******************");
    if(eventObject.action == 'opened'){
        await pullRequestOpenModule.pullRequestOpenMain(pool, eventObject, context);
    }else if(eventObject.action == 'closed'){
        await pullRequestClosedModule.pullRequestCloseMain(pool, eventObject, context);
    }else if (eventObject.action == 'labeled') {
        await pullRequestLabeledModule.pullRequestLabeledMain(pool, eventObject, context);
    } else {
        // todo: handle syncronized and ects. 현재 opened/closed 외에는 개발 진행 X. do nothing.
    }
}
module.exports.pullRequestMain = pullRequestMain;