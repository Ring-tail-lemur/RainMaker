const { ConnectionContextBase } = require('@azure/core-amqp');
const open_module = require('./open.js');
const close_module = require('./close.js');
module.exports = {
    async pullRequestMain(context, hookBody, cloudEventObj){
        const resultObj = new Object();
        cloudEventObj.action = JSON.stringify(hookBody["action"]).replace(/['"]+/g, '');
        cloudEventObj.pull_request_remote_identifier = JSON.stringify(hookBody.pull_request.id).replace(/['"]]+/g, '');
        cloudEventObj.repository_name = JSON.stringify(hookBody.repository.name).replace(/['"]+/g, ''); 
        cloudEventObj.repository_identifier = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_name = JSON.stringify(hookBody.repository.owner.login).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_id = JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
        cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
        if(cloudEventObj.action == 'opened'){
            context.log("opened event occured");
            resultObj = await open_module.pullRequestOpen(hookBody,cloudEventObj);
        }else if(cloudEventObj.action == 'closed'){
            context.log("closed event occured");
            resultObj = await close_module.pullRequestClose(hookBody,cloudEventObj);
        }else{
            context.log("not yet finished.");
        }
        return resultObj;
    }
}