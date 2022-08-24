UPDATE release_success
SET time_to_restore_service_process_end = 0
WHERE time_to_restore_service_process_end = 1;
UPDATE issue
SET failed_change_process_end = 0
WHERE failed_change_process_end = 1;
update release
SET lead_time_for_change_process_end = 0
WHERE lead_time_for_change_process_end = 1;
update release
SET change_failure_rate_process_end = 0
WHERE change_failure_rate_process_end = 1;
UPDATE pull_request
SET lead_time_for_change_process_end = 0
WHERE lead_time_for_change_process_end = 1;
UPDATE pull_request_direction
SET lead_time_for_change_process_end = 0
WHERE lead_time_for_change_process_end = 1;
