const request =require('request');

const getModule = require('./http-protocol-module');

async function getCommitsWithToken(context, uri, gitHubtokenP,pull_request_remote_identifier){
  const githubToken = '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';
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + githubToken}
  };
  const commitList =  await getModule.httpProtocolCustom(context, options);
  // context.log("--------- getCommitModule ---------\n"+commitList+"\n--------- getCommitModule ---------\n");
  return commitList;
}

async function getCommitsAnyToken(context, commits_uri,pull_request_remote_identifier){
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };
  const commitList =  await getModule.httpProtocolCustom(context, options);
  // context.log("--------- getCommitModule ---------\n"+commitList+"\n--------- getCommitModule ---------\n");
  return commitList;
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  
// 테스트 제발 좀