const {Octokit} = require("@octokit/core");

async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, labelName) {
    const octokit = new Octokit({
        auth: token
    })
    return await octokit.request(`GET /repos/${ownerName}/${repositoryName}/labels/${labelName}`)
}


module.exports.createGitHubLabel = createGitHubLabel;
