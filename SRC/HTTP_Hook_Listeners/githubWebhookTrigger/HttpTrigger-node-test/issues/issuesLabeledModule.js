async function issueLabledMain(hookBody,cloudEventObj, context) {
    cloudEventObj.labelNameList = await issueLabeling(hookBody,context);
    cloudEventObj.action_time = JSON.stringify(hookBody.issue.updated_at).replace(/['"]+/g, '');
    return cloudEventObj;
}

async function issueLabeling(hookBody,context){
    const labels = hookBody.issue.labels;
    let labeledList = new Array();
    const labelsCnt = labels.length;
    if(labelsCnt < 1){
        throw new Error("nothing labeled");
    }
    for(let i = 0; i < labelsCnt; i++) {
        labeledList.push(JSON.stringify(labels[i].name).replace(/['"]+/g, ''));
    }
    return labeledList.toString();
}

module.exports.issueLabledMain = issueLabledMain;
module.exports.issueLabeling = issueLabeling;