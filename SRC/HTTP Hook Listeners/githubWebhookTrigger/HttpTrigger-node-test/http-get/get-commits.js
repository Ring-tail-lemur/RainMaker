const request =require('request');

const getModule = require('./http-protocol-module');

async function getCommitsWithToken(context, uri, gitHubtokenP){
  context.log("getCommitsWithToken from : " + uri);
  const githubToken = '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';
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + githubToken}
  };
  await getModule.httpProtocolCustom(context, options);
}

async function getCommitsAnyToken(context, commits_uri){
  context.log("getCommitsWithoutToken from : "+ commits_uri);
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };
  await getModule.httpProtocolCustom(context, options);
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  
