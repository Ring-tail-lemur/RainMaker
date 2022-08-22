INSERT INTO release_success(release_id, repository_id, released_at)
SELECT release_id, release.repository_id, release.published_at
FROM release
WHERE change_failure_rate_process_end = 0;

UPDATE release
SET change_failure_rate_process_end = 1
WHERE change_failure_rate_process_end = 0;



UPDATE release_success
SET release_success.failed_at = ie.event_time,
    first_error_issue_id      = ie.issue_id,
    release_success.is_success= 0
FROM release_success
         JOIN issue_label il ON release_success.release_id = il.release_id
         JOIN issue i ON i.issue_label_id = il.issue_label_id
         JOIN issue_event ie ON ie.issue_id = i.issue_id
WHERE release_success.is_success = 1
  AND i.failed_change_process_end = 0
  AND ie.issue_event_type = 'OPEN'

UPDATE issue
set failed_change_process_end = 1
where failed_change_process_end = 0
