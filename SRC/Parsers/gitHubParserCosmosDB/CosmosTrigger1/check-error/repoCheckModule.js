const { Octokit } = require("@octokit/core");
const repositoryCreateRepository = require('../repository/repositoryCreateRepository');

const owner = 'Ring-tail-lemur';
const repo = 'test-for-fake-project';

async function repoCheckAndInsert(owner, repo) {

    const octokit = new Octokit({
        auth: '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'
    })

    const response = await octokit.request(`GET /repos/${owner}/${repo}`);

    try {
        let repo_id;

        if(response.data.owner.type == "User"){
            repo_id = await repositoryCreateRepository.insertRepoByUserId(response.data.name, response.data.id, response.data.owner.id);
        } else if(response.data.owner.type == "Organization") {
            repo_id = await repositoryCreateRepository.insertRepoByOrganizationId(response.data.name, response.data.id, response.data.owner.id);
        }
        return repo_id;
    } catch(err) {
        console.error(err);
        return false;
    }

}

module.exports.repoCheckAndInsert = repoCheckAndInsert;