const { Octokit } = require("@octokit/core");
// const msSQLPool = require('../ms-sql/msSQLPool')

async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, context) {
    const octokit = new Octokit({
        auth: token
    })
    let response;
    try {
        context.log(`POST /repos/${ownerName}/${repositoryName}/labels`);
        context.log(`ownerName : ${ownerName}, repo : ${repositoryName}, name : ${releaseName}`);
        response = await octokit.request(`POST https://api.github.com/repos/${ownerName}/${repositoryName}/labels`, {
            owner: ownerName,
            repo: repositoryName,
            name: `[RainMaker]runtime-error-${releaseName}`,
            description: 'RainMaker runtime error label',
            color: 'f213be'
        });
    } catch (e){
        if (e.status === 401) {
            context.log("토큰 인증 에러");
            throw "토큰 인증 에러";
        } else if(e.status === 422) {
            context.log("같은 이름의 Label이 이미 존재합니다.");
            throw "같은 이름의 Label이 이미 존재합니다.";
            //처리해야함
        } else if(e.status === 404) {
            context.log(`POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
            throw "주소가 유효하지 않음";
        }
    }
}

module.exports.createGitHubLabel = createGitHubLabel;