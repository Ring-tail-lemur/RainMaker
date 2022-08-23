const { Octokit } = require("@octokit/core");
const labelCreateRepository = require('../label/labelRepository');
const getLabelApiModule = require('./getLabelApiModule');
const msSQLPool = require('../ms-sql/msSQLPool')

async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, context, releaseId, dbConnectionPool) {
    const octokit = new Octokit({
        auth: token
    })
    let response;
    try {
        response = await octokit.request(`POST /repos/${ownerName}/${repositoryName}/labels`, {
            owner: ownerName,
            repo: repositoryName,
            name: `runtime-error-${releaseName}`,
            description: 'RainMaker runtime error label',
            color: 'f213be'
        });
    } catch (e){
        if (e.status === 401) {
            throw "토큰 인증 에러";
        } else if(e.status === 422) {
            throw "같은 이름의 Label이 이미 존재하니다.";
            //처리해야함
        } else if(e.status === 404) {
            context.log(`POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
            throw "주소가 유효하지 않음";
        }
    }
    labelCreateRepository.insertIssueLabel(dbConnectionPool, releaseId, response.data.id, response.data.name, repositoryId, context)
}


module.exports.createGitHubLabel = createGitHubLabel;
(async() => {
    console.log('before start');
    await createGitHubLabel("test",1, "test-for-fake-project", "Ring-tail-lemur", "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", console, 1, await msSQLPool);
    console.log('after start');
})();
