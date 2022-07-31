const request =require('request');

const getModule = require('./http-protocol-module');

async function getCommitsWithToken(context, uri, gitHubtokenP){
  context.log("getCommitsWithToken from : " + uri);
  const githubToken = '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';
  const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + githubToken}
  };
  request(options, function(err,response,body){
    if(err){
        console.log(err);
    }else{
        console.log(body);
    }
  });
}


async function getCommitsAnyToken(context, commits_uri){
  context.log("getCommitsWithoutToken from : "+ commits_uri);
  const options = {
    uri: commits_uri,
    headers: {
      'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
  };

  resultCommitList = await getModule.httpProtocolCustom(context, options);
  resultCommitList = await getModule.testHTTP(context);
  return resultCommitList;
}

module.exports.getCommitsWithToken = getCommitsWithToken;
module.exports.getCommitsWithoutToken = getCommitsAnyToken;
  
/*
예시코드... 곧 지울 예정
console.log("hi");
// const uri = 'http://api.github.com/repos/Ring-tail-lemur/test-for-fake-project/pulls/78/commits';
const uri = 'https://api.github.com/repos/Ring-tail-lemur/RainMaker/pulls/13/commits';
const token = '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';
const options = {
  uri: uri,
  headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit', 'Authorization': 'Bearer ' + token}
};
request(options, function(err,response,body){
  if(err){
    console.log(err);
  }else{
    const jsoned = JSON.parse(body);
    const jsonedLength = jsoned.length;
    for(i = 0; i < jsonedLength; i++){
      console.log(jsoned[i]);
    }
  }
});
*/