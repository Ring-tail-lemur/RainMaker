const { Octokit } = require("@octokit/core");
const repositoryCreateRepository = require('../repository/repositoryCreateRepository');
const errLogModule = require('../utils/slackLogBot.js');

const owner = 'Ring-tail-lemur';
const repo = 'test-for-fake-project';

async function repoCheckAndInsert(pool, owner, repo) {

    // todo 여기에 자신의 팀의 토큰을 넣어야한다. 그래야 repo 확인 가능
    const octokit = new Octokit({
        auth: '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'
    })

    const response = await octokit.request(`GET /repos/${owner}/${repo}`);

    try {
        let repo_id;

        if(response.data.owner.type == "User"){
            repo_id = await repositoryCreateRepository.insertRepoByUserId(pool, response.data.name, response.data.id, response.data.owner.id);
        } else if(response.data.owner.type == "Organization") {
            repo_id = await repositoryCreateRepository.insertRepoByOrganizationId(pool, response.data.name, response.data.id, response.data.owner.id);
        }
        return repo_id;
    } catch(err) {
        errLogModule.log(err, "repoCheckModule.js");
        return false;
    }

}

module.exports.repoCheckAndInsert = repoCheckAndInsert;