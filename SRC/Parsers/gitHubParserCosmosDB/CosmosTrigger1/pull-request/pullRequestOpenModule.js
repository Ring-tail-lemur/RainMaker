const pool = require('../ms-sql/msSQLPool');


async function pullRequestOpenMain(eventObject, context) {
     
    //pull_request entity 생성 및 삽입
    let pullRequestEntity = new Object();
    pullRequestEntity.remote_identifier = NUMBER(eventObject.pull_request_remote_identifier);
    pullRequestEntity.pull_request_number = NUMBER(eventObject.pull_request_remote_identifier);
    const pullRequestInsertQuery = `
        INSERT INTO pull_request (remote_identifier, pull_request_number, repository_id,
        pull_request_open_branch_id, pull_request,close_branch_id, 
        created_date, modified_date, process_end)
        VALUES ('${eventObject.pull_request_remote_identifier}',
            '${eventObject.pull_request_opened_number}',
            '(
                SELECT repository_id FROM repository WHERE remote_identifier = ${eventObject.repository_identifier}
            ),
            (
                SELECT branch_id FROM branch WHERE name LIKE ${eventObject.pull_request_open_branch}
                    AND repository_id LIKE (
                            SELECT repository_id FROM repository WHERE remote_identifier = ${eventObject.repository_identifier}
                    )
            ),
            (
                SELECT branch_id FROM branch WHERE name LIKE ${eventObject.pull_request_open_branch}
                    AND repository_id LIKE (
                            SELECT repository_id FROM repository WHERE remote_identifier = ${eventObject.repository_identifier}
                    )
            ),
            '${eventObject.pull_request_open_time}',
            '${eventObject.pull_request_open_time}',
            0`;
    await dbConnectionPool.request().query(pullRequestInsertQuery);
    
    //pull_request_direction entity 생성 및 삽입
    const pullRequestDirectionInsertQuery = `
        INSERT INTO pull_request_commit_table (source_pull_request_id, outgoing_pull_request_id, 
            created_date, modified_date, process_end)
        VALUES ( 
            (SELECT pull_request_id FROM pull_request 
                WHERE pull_request_id = ${eventObject.pull_request_remote_identifier}),
            (SELECT pull_request_id FROM pull_request 
                WHERE pull_request_id = ${eventObject.pull_request_remote_identifier}),
            '${eventObject.pull_request_open_time}',
            '${eventObject.pull_request_open_time}',
            0
        )
    `;
    await dbConnectionPool.request().query(pullRequestDirectionInsertQuery);

    //pull_request_event entity 생성 및 삽입
    const pullRequestEventInsertQuery = `
        INSERT INTO pull_request_event (event_type, event_time, pull_request_id, event_sender_id, created_date, modified_date)
        VALUES (
            
        )
    `;
}

module.exports.pullRequestOpenMain = pullRequestOpenMain;

/*
pullRequestOpen 이벤트 sample
{
    "hook_event": "pull_request",
    "source": "github",
    "action": "opened",
    "pull_request_remote_identifier": "1014736314",
    "repository_name": "test-for-fake-project",
    "repository_identifier": "510731046", 
    "repository_owner_type": "Organization", 
    "repository_owner_name": "Ring-tail-lemur",
    "repository_owner_id": "107110653", 
    "repository_private": "false",
    "pull_request_opened_number": "184",
    "pull_request_open_branch": "test",
    "pull_request_open_time": "2022-08-02T07:44:13Z",
    "EventProcessedUtcTime": "2022-08-02T07:44:15.8134619Z",
    "PartitionId": 1,
    "EventEnqueuedUtcTime": "2022-08-02T07:44:15.542Z"
}
*/