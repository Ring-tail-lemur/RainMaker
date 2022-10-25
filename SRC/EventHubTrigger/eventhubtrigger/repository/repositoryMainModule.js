const repositoryCreateMainModule = require("./repositoryCreateModule");

async function repositoryMain(pool, eventObject, context){
    //현재는 created 외에는 개발 진행 X, 그러므로 생성 외에는 그냥 어떤것도 하지 않는 것으로 개발 요망.
    if(eventObject.action == 'created') {
        await repositoryCreateMainModule.repositoryCreateMain(pool, eventObject);
    }

}

module.exports.repositoryMain = repositoryMain;