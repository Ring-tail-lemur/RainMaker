const issueRepository = require('./issueRepository');

async function issueMain(pool, eventObject, context){

    console.log(eventObject);
    if(eventObject.action == 'opened') {
        console.log("eventObjecct", eventObject.action);
        await issueRepository.insertIssueByRepoIdAndLabelId(pool, eventObject.issue_remote_id, eventObject.repository_remote_id, eventObject.actor_remote_id, eventObject.action, eventObject.);
    } else if (eventObject.action == 'labeled' || eventObject.action == 'unlabeled' || eventObject.action == 'edited' || eventObject.action == 'reopened' || eventObject.action == 'closed') {
        console.log("eventObjecct", eventObject.action);
    } else if (eventObject.action == 'deleted') {
        console.log("eventObjecct", eventObject.action);
    }
}

async function getlabel() {

}

module.exports.issueMain = issueMain;