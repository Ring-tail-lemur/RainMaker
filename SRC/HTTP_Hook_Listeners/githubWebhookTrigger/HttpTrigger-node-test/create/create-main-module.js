const branchModule = require('./branchModule.js');
const tagModule = require('./tagModule.js');
async function createMain(context, hookBody, cloudEventObj){
    cloudEventObj.ref_type = JSON.stringify(hookBody.ref_type).replace(/['"]+/g, '');
    if(cloudEventObj.ref_type == 'branch'){
        cloudEventObj.action = 'created_branch';
        return await branchModule.branchMain(hookBody, cloudEventObj, context);
    }else if(cloudEventObj.ref_type == 'tag'){
        cloudEventObj.action = 'created_tag';
        return await tagModule.tagMain(hookBody, cloudEventObj, context);
    }else{
        return new Object();
    }
    

}

module.exports.createMain = createMain;