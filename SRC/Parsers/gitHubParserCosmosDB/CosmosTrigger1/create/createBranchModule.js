const pool = require('../ms-sql/msSQLPool');
const repoCheckModule = require('../check-error/repoCheckModule');
const createBranchRepository = require('./createBranchRepository');

async function createBranchMain(eventObject, context) {
    // branch entity 생성 및 삽입

    const dbConnectionPool = await pool;

    const sqlRepoQuery = 
    `
    SELECT repository_id
    FROM repository
    WHERE remote_identifier = ${eventObject.repository_id}
    `;
    /*
    1. 처음에는 repo_id를 찾아오는 쿼리를 날린다.
    2. 만약 repo_id가 없다면, 진짜 repo_id가 없는지 token과 url를 통해 github에 정보를 요청한다.
    3-1. 만약 정말로 repo가 없는 경우라면, 이 요청을 버린다.
    3-2. 만약 repo가 있는 경우는, repo 정보를 insert하고, 그 insert된 id값과 함께 INSERT 쿼리를 다시 날린다. 
    */
    const repository_id = await dbConnectionPool.request()
        .query(sqlRepoQuery);
    
    
    if(!repository_id.recordset[0]) { 
        // 만약 가져온 repo가 없다면
        const ownerAndRepo = eventObject.repository_full_name.split('/');
        const owner = ownerAndRepo[0];
        const repo = ownerAndRepo[1];
        const repo_id = await repoCheckModule.repoCheckAndInsert(owner, repo);
        // 리포가 진짜 존재하는지 체크하고 INSERT한다. INSERT한 리포의 repo_id를 반환
        if(repo_id) await createBranchRepository.insertBranchByRepoIdAndUserId(eventObject.branch_name, repo_id, eventObject.author_id);

    } else {
        await createBranchRepository.insertBranchByRepoRemoteIdAndUserId(eventObject.branch_name, eventObject.repository_id, eventObject.author_id);
    }

}

module.exports.createBranchMain = createBranchMain;


/*
브랜치 이벤트 sample
{
    "hook_event":"create",
    "source":"github",
    "action":"create_ref",
    "ref_type":"branch",
    "branch_name":"sibalzzz",
    "author_id":"81180977",
    "repository_id":"510731046",
    "repository_full_name":"Ring-tail-lemur/test-for-fake-project",
    "organization_id":"107110653",
}
*/