with closed_issue as
         (select release_success_id, event_time as closed_issue_event_time
          from release_success
                   JOIN issue ON release_success.first_error_issue_id = issue.issue_id
                   JOIN issue_event ON issue.issue_id = issue_event.issue_id
          where issue_event_type = 'CLOSED'),
     opened_issue as
         (select release_success_id, event_time as opened_issue_event_time
          from release_success
                   JOIN issue ON release_success.first_error_issue_id = issue_id
                   JOIN issue_event ON issue.issue_id = issue_event.issue_id
          where issue_event_type = 'OPEN')


INSERT
INTO time_to_restore_service (release_success_id, restore_service_time, restored_at)
SELECT closed_issue.release_success_id,
       DATEDIFF(minute, opened_issue.opened_issue_event_time,
                closed_issue.closed_issue_event_time) as restore_service_time,
       closed_issue.closed_issue_event_time
FROM closed_issue
         join opened_issue
              on closed_issue.release_success_id = opened_issue.release_success_id;
