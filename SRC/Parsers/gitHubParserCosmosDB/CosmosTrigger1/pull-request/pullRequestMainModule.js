async function pullRequestMain(eventObject,context){
    if(eventObject.action == 'opened'){
        
    }else if(eventObject.action == 'closed'){

    }else{
        //todo: handle syncronized and ects. 현재 opened/closed 외에는 개발 진행 X. do nothing.
    }
}
module.exports.pullRequestMain = pullRequestMain;