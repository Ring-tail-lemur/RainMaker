const { Octokit } = require("@octokit/core");
const err_log_module = require('slackLogBot.js');
// const msSQLPool = require('../ms-sql/msSQLPool')

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
        context.log("response : " + response);
        
    } catch (e){
        if (e.status === 401) {
            err_log_module.log(e, "labelCreateRestAPI.js 401 토큰 인증 에러");
            context.log("토큰 인증 에러");
            throw "토큰 인증 에러";
        } else if(e.status === 422) {
            err_log_module.log(e, "labelCreateRestAPI.js 422 같은 이름의 Label이 이미 존재합니다.");
            context.log("같은 이름의 Label이 이미 존재합니다.");
            throw "같은 이름의 Label이 이미 존재합니다.";
            //처리해야함
        } else if(e.status === 404) {
            err_log_module.log(e, `labelCreateRestAPI.js 404 토큰 인증 에러 POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
            context.log(`POST /repos/${ownerName}/${repositoryName}/labels 요청이 유효하지 않습니다.`);
            throw "주소가 유효하지 않음";
        }
    }
}

module.exports.createGitHubLabel = createGitHubLabel;