INSERT
INTO time_to_restore_service (release_success_id, restore_service_time, restored_at, repository_id)
SELECT closed_issue.release_success_id,
       DATEDIFF(minute, opened_issue.opened_issue_event_time,
                closed_issue.closed_issue_event_time) as restore_service_time,
       closed_issue.closed_issue_event_time,
       opened_issue.repository_id
FROM closed_issue
         join opened_issue
              on closed_issue.release_success_id = opened_issue.release_success_id
WHERE time_to_restore_service_process_end = 0;

UPDATE release_success
SET release_success.time_to_restore_service_process_end = 1
FROM issue_event JOIN release_success ON release_success.first_error_issue_id = issue_event.issue_id
WHERE time_to_restore_service_process_end = 0
  AND issue_event_type = 'CLOSED'
