const { Octokit } = require("@octokit/core");

async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, context) {
    let response;
    const octokit = new Octokit({
        auth: token
    });
    try {
        context.log(`POST /repos/${ownerName}/${repositoryName}/labels`);
        context.log(`ownerName : ${ownerName}, repo : ${repositoryName}, name : ${releaseName}`);
        response = await octokit.request(`POST /repos/${ownerName}/${repositoryName}/labels`, {
            owner: ownerName,
            repo: repositoryName,
            name: `[RainMaker]runtime-error-${releaseName}`,
            description: 'RainMaker runtime error label',
            color: 'f213be'
        });
        context.log("Issue Label 생성 완료");
    } catch (e){
        if (e.status === 401) {
            context.log("토큰 인증 에러");
        } else if(e.status === 422) {
            context.log("같은 이름의 Label이 이미 존재합니다.");
        } else if(e.status === 404) {
            context.log(`POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
        }
    }
}

module.exports.createGitHubLabel = createGitHubLabel;