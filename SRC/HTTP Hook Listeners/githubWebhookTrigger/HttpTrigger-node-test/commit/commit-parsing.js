const sendModule = require('../event-hub/send.js');
const request = require('request');

async function parsingCommit(context, commitObj, parent_pull_request_remote_identifier){
    const eventHubCommitObj = new Object();
    eventHubCommitObj.source = 'github';
    eventHubCommitObj.parent_pull_request_remote_identifier = parent_pull_request_remote_identifier;
    eventHubCommitObj.commit_sha = JSON.stringify(commitObj.sha).replace(/['"]+/g, '');
    eventHubCommitObj.parent_commit_sha = JSON.stringify(commitObj.parents.sha);
    eventHubCommitObj.commit_author_name = JSON.stringify(commitObj.commit.author.name).replace(/['"]+/g, '');
    eventHubCommitObj.commit_author_email = JSON.stringify(commitObj.commit.author.email).replace(/['"]+/g, '');
    eventHubCommitObj.commit_message = JSON.stringify(commitObj.message);
    
    let remote_id = '';
    const url = 'https://api.github.com/users/'+eventHubCommitObj.name;
    context.log(url);
    options = {
        uri: url,
        headers: {'User-Agent':  'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
    }
    // await request(options, await function(err,response,body){
    //     if(err){
    //         context.log(err);
    //     }else{
    //         const jsoned = JSON.parse(body);
    //         remote_id = JSON.stringify(jsoned.id).replace(/['"]+/g, '');
    //     }
    // });
    await axios({
        method: 'GET',
        url: options.uri,
        headers: options.headers,
        }).then(function(response){
            const nameData = response.data;
            remote_id = JSON.stringify(nameData.id).replace(/['"]+/g, '');
            
        }).catch(function(err){
            context.log(err);
    });
    eventHubCommitObj.commit_author_remote_id = remote_id;
    
    
    context.log(JSON.stringify(eventHubCommitObj));
    sendModule.sender(eventHubCommitObj);
}


async function getNameFromCommit(context, name){
    let remote_id = '';
    const url = 'https://api.github.com/users/'+name;
    context.log(url);
    options = {
        uri: url,
        headers: {'User-Agent':  'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.webkit'}
    }
    await request(options, function(err,response,body){
        if(err){
            context.log(err);
        }else{
            const jsoned = JSON.parse(body);
            remote_id = JSON.stringify(body.id).replace(/['"]+/g, '');
        }
    });
    return remote_id;
}
module.exports.parsingCommit = parsingCommit;
module.exports.getNameFromCommit = getNameFromCommit;