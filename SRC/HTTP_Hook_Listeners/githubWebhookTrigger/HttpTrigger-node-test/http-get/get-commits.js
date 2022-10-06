const request =require('request');

const getModule = require('./http-protocol-module');

async function getCommitsWithToken(context, uri, gitHubTokenP,pull_request_remote_identifier){
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + gitHubTokenP}
  };
  const commitList =  await getModule.httpProtocolCustom(context, options);
  return commitList;
}

async function getCommitsAnyToken(context, commits_uri,pull_request_remote_identifier){
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };
  const commitList =  await getModule.httpProtocolCustom(context, options);
  return commitList;
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  