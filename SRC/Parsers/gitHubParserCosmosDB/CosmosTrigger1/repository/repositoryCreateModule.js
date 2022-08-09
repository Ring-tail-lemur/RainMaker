const repositoryCreateRepository = require('./repositoryCreateRepository');

async function repositoryCreateMain(eventObject, context){
    //repository entity 생성 및 삽입

    if(eventObject.repository_owner_type == "Organization") {
        repositoryCreateRepository.insertRepoByOrganizationId(eventObject.repository_name, eventObject.repository_remote_id, eventObject.repository_owner_id);
    } else if (eventObject.repository_owner_type == "User") {
        repositoryCreateRepository.insertRepoByUserId(eventObject.repository_name, eventObject.repository_remote_id, eventObject.repository_owner_id);
    }
    
}
module.exports.repositoryCreateMain = repositoryCreateMain;

/*
레포지토리 생성 이벤트
{
    "hook_event":"repository",
    "source":"github",
    "action":"created",
    "repository_remote_id":"520479928",
    "repository_name":"dfsasdfsf",
    "repository_owner_type":"Organization",
    "repository_owner_id":"107110653"
}

/*
레포지토리 생성 이벤트
{

}

*/