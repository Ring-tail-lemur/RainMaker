async function createBranchMain(eventObject, context) {
    // branch entity 생성 및 삽입
}
module.exports.createBranchMain = createBranchMain;


/*
브랜치 이벤트 sample
{
    "hook_event":"create",
    "source":"github",
    "action":"create_ref",
    "ref_type":"branch",
    "branch_name":"ㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㄹ",
    "author_id":"33488236",
    "organization_id":"107110653"
}
*/