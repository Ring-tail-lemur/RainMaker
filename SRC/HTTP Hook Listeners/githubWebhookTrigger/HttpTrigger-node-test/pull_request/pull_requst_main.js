
module.exports = {
    async pull_request(inputObject, cloudEventObj){
       const action = JSON.stringify(inputObject.action).replace(/['"]+/g, '');
       cloudEventObj.action = action;


       return cloudEventObj;
    }
}