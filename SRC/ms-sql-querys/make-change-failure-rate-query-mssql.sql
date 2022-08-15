INSERT INTO failed_change (release_id, first_error_issue_id, repository_id, failed_at)
select issue_label.release_id,
       issue.issue_id,
       issue.repository_id,
       issue_event.event_time
from issue
         join issue_event on issue.issue_id = issue_event.issue_id
         join issue_label on issue_label.issue_id = issue.issue_id
         join release on issue_label.release_id = release.release_id
         left join failed_change
                   on release.release_id = failed_change.release_id
where issue.failed_change_process_end = 0
  and failed_change.failed_change_id is null
  and issue_event.issue_event_type = 'OPEN'


UPDATE issue
set failed_change_process_end = 1
where failed_change_process_end = 0
