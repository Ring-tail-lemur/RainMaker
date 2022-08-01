const request =require('request');

const getModule = require('./http-protocol-module');

async function getCommitsWithToken(context, uri, gitHubtokenP,pull_request_remote_identifier){
  const githubToken = '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';
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + githubToken}
  };
  return (await getModule.httpProtocolCustom(context, options,pull_request_remote_identifier));
}

async function getCommitsAnyToken(context, commits_uri,pull_request_remote_identifier){
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };
  return (await getModule.httpProtocolCustom(context, options,pull_request_remote_identifier));
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  
// 테스트 제발 좀