const issueRepository = require('./issueRepository');

async function issueMain(pool, eventObject, context){

    console.log(eventObject);

    let issue_label_id;
    if(eventObject.label_name_list && eventObject.label_id_list) {
        issue_label_id = getLabelId(eventObject.label_name_list, eventObject.label_id_list);
    } else {
        issue_label_id = null;
    }
    console.log("issue_label_id", issue_label_id);
    if(eventObject.action == 'opened') {
        await issueRepository.insertIssueByRepoIdAndLabelId(pool, eventObject.issue_remote_id, eventObject.repository_remote_id, eventObject.actor_remote_id, eventObject.state, issue_label_id, eventObject.issue_number, context);
        await issueRepository.insertIssueEventByIssueIdAndUserId(pool, eventObject.X_GitHub_Delivery, eventObject.action, eventObject.action_time, eventObject.actor_remote_id, eventObject.issue_remote_id, context);
    } else if (eventObject.action == 'labeled' || eventObject.action == 'unlabeled' || eventObject.action == 'edited' || eventObject.action == 'reopened' || eventObject.action == 'closed') {
        await issueRepository.updateIssueByIssueIdAndRepoId(pool, eventObject.issue_remote_id, eventObject.repository_remote_id, eventObject.state, issue_label_id);
        await issueRepository.insertIssueEventByIssueIdAndUserId(pool, eventObject.X_GitHub_Delivery, eventObject.action, eventObject.action_time, eventObject.actor_remote_id, eventObject.issue_remote_id, context);
    } else if (eventObject.action == 'deleted') {
        // todo : delete됐을 경우를 처리해야함.
    }
}

function getLabelId(label_name_list, label_id_list) {

    const label_name_arr = label_name_list.split(',');
    const label_id_arr = label_id_list.split(',');

    let arr_id;
    for (let i = 0; i< label_name_arr.length; i++) {
        let label_name = label_name_arr[i];
        if(label_name.includes('[RainMaker]runtime-error-')){
            arr_id = i;
            break;
        }
    }
    return label_id_arr[arr_id];
}

module.exports.issueMain = issueMain;