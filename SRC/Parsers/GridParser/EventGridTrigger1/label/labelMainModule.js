const labelRepository = require('./labelRepository');

async function labelMain(pool, eventObject,context){
    if(eventObject.action == 'created') {
        await labelRepository.insertIssueLabel(pool, eventObject.issue_label_id, eventObject.label, eventObject.repository_id);
    } else if (eventObject.action == 'deleted') {
        await labelRepository.deleteIssueLabel(pool, eventObject.issue_label_id, eventObject.repository_id);
    } else if (eventObject.action == 'edited') {
        await labelRepository.deleteIssueLabel(pool, eventObject.issue_label_id, eventObject.repository_id);
        await labelRepository.insertIssueLabel(pool, eventObject.issue_label_id, eventObject.label, eventObject.repository_id);
    }
}
module.exports.labelMain = labelMain;