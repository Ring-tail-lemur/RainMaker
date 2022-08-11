async function createMain(context, hookBody, cloudEventObj){
    cloudEventObj.ref_type = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
    if(cloudEventObj.ref_type == 'branch'){
        //branch 생성 이벤트
        
    }
    

}

module.exports.createMain = createMain;