BEGIN TRAN

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
FROM release
         JOIN run_time_error_labels il on release.name = il.release_name
         JOIN release_success
              ON release_success.release_id = release.release_id
         JOIN issue i ON i.issue_label_id = il.issue_label_id
         JOIN issue_event ie ON ie.issue_id = i.issue_id
WHERE ie.issue_event_type = 'OPENED'
  AND i.failed_change_process_end = 0
  AND release_success.is_success = 1;
COMMIT TRAN

BEGIN TRAN

UPDATE issue
SET failed_change_process_end = 1
FROM issue i
         JOIN issue_event ie ON ie.issue_id = i.issue_id
where failed_change_process_end = 0
  AND ie.issue_event_type = 'OPENED';

COMMIT TRAN
