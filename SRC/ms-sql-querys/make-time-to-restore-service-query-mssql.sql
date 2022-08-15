with closed_issue as
         (select failed_change_id, event_time as closed_issue_event_time
          from failed_change
                   JOIN issue ON failed_change.first_error_issue_id = issue.issue_id
                   JOIN issue_event ON issue.issue_id = issue_event.issue_id
          where issue_event_type = 'CLOSED'),
     opened_issue as
         (select failed_change_id, event_time as opened_issue_event_time
          from failed_change
                   JOIN issue ON failed_change.first_error_issue_id = issue_id
                   JOIN issue_event ON issue.issue_id = issue_event.issue_id
          where issue_event_type = 'OPENED')


INSERT
INTO time_to_restore_service (failed_change_id, restore_service_time)
SELECT closed_issue.failed_change_id,
       DATEDIFF(minute, opened_issue.opened_issue_event_time,
                closed_issue.closed_issue_event_time) as restore_service_time
FROM closed_issue
         join opened_issue
              on closed_issue.failed_change_id = opened_issue.failed_change_id;