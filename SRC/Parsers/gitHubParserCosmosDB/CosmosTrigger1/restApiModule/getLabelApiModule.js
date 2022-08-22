const {Octokit} = require("@octokit/core");

async function createGitHubLabel(releaseName, repositoryId, repositoryName, ownerName, token, labelName) {
    const octokit = new Octokit({
        auth: token
    })
    return await octokit.request(`GET /repos/${ownerName}/${repositoryName}/labels/${labelName}`)
}


module.exports.createGitHubLabel = createGitHubLabel;
// (async () => {
//     console.log('before start');
//     await createGitHubLabel("hello", 1, "test-for-fake-project", "Ring-tail-lemur", "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", "runtime-error-hello");
//     console.log('after start');
// })();
