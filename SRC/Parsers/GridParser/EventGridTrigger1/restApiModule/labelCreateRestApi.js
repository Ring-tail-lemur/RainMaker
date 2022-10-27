const { Octokit } = require("@octokit/core");
// const msSQLPool = require('../ms-sql/msSQLPool')
const err_log_module = require('../utils/slackLogBot.js');
async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, context) {
    const octokit = new Octokit({
        auth: token
    })
    let response;
    try {
        response = await octokit.request(`POST /repos/${ownerName}/${repositoryName}/labels`, {
            owner: ownerName,
            repo: repositoryName,
            name: `[RainMaker]runtime-error-${releaseName}`,
            description: 'RainMaker runtime error label',
            color: 'f213be'
        });
    } catch (e){
        if (e.status === 401) {
            err_log_module.log(e, "토큰 인증 에러 / labelCreateRestApi");
            throw "토큰 인증 에러";
        } else if(e.status === 422) {
            err_log_module.log(e, "같은 이름의 Label이 이미 존재합니다. / labelCreateRestApi");
            throw "같은 이름의 Label이 이미 존재하니다.";
            //처리해야함
        } else if(e.status === 404) {
            err_log_module.log(e, `POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다. / labelCreateRestApi`);
            context.log(`POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
            throw "주소가 유효하지 않음";
        }
    }
}

module.exports.createGitHubLabel = createGitHubLabel;