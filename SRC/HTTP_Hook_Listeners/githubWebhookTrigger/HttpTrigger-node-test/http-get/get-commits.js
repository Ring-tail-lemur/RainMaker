const getModule = require('./http-protocol-module');
const err_log_module = require('../utils/slackLogBot.js');
async function getCommitsWithToken(context, uri, gitHubTokenP,pull_request_remote_identifier){
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + gitHubTokenP}
  };
  try{
    const commitList =  await getModule.httpProtocolCustom(context, options);
    // context.log("--------- getCommitModule ---------\n"+commitList+"\n--------- getCommitModule ---------\n");
    return commitList;
  }catch(e){
    err_log_module.log(e,"get-commits.js // getCommitsWithToken");
  }
}

async function getCommitsAnyToken(context, commits_uri,pull_request_remote_identifier){
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };
  try{
    const commitList =  await getModule.httpProtocolCustom(context, options);
    // context.log("--------- getCommitModule ---------\n"+commitList+"\n--------- getCommitModule ---------\n");
    return commitList;
  }catch(e){
    err_log_module.log(e,"get-commits.js // getCommitsWithToken");
  }
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  