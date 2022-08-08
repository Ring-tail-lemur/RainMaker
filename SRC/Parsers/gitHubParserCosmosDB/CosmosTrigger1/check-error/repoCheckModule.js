const { Octokit } = require("@octokit/core");
const repositoryCreateRepository = require('../repository/repositoryCreateRepository');

const owner = 'Ring-tail-lemur';
const repo = 'test-for-fake-project';

async function repoCheckAndInsert(owner, repo) {

    const octokit = new Octokit({
        auth: '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'
    })

    try {
        const response  = await octokit.request(`GET /repos/${owner}/${repo}`);
        console.log(response.data.owner.id, response.data.owner.type, response.data.name, response.data.id);
        if(response.data.owner.type == "User"){
            repositoryCreateRepository.insertRepoByUserId(response.data.name, response.data.id, response.data.owner.id);
        } else if(response.data.owner.type == "Organization") {
            const repo_id = repositoryCreateRepository.insertRepoByOrganizationId(response.data.name, response.data.id, response.data.owner.id);
        }
        return repo_id;
    } catch(err) {
        console.error(err);
        return false;
    }

}
console.log(repoCheckAndInsert(owner, repo));

module.exports.repoCheckAndInsert = repoCheckAndInsert;