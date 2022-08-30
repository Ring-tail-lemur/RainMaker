INSERT INTO lead_time_for_change
(pull_request_id, first_commit_time, pr_open_time, first_review_time, pr_close_time, repository_id)
SELECT target_pr.pr_id           AS pull_request_id,
       isnull(commits.first_commit_time, pre.event_time) AS first_commit_time,
       pre.event_time            AS pr_open_time,
       isnull(review.first_review_time, pr_close_time)  AS first_review_time,
       target_pr.pr_close_time   AS pr_close_time,
       target_pr.repo_id         as repository_id
FROM (SELECT pr.pull_request_id  AS pr_id,
             pr_event.event_time AS pr_close_time,
             pr.repository_id    as repo_id
      FROM pull_request_event pr_event
               JOIN pull_request pr
                    ON pr_event.pull_request_id = pr.pull_request_id
               LEFT JOIN
           pr_direction_source_count pr_source
           ON pr_source.pr_id = pr.pull_request_id
      WHERE pr_event.pull_request_event_type = 'CLOSED'
        AND pr.lead_time_for_change_process_end = 0
        AND pr_source.source_count IS NULL) target_pr
         LEFT JOIN
     pr_first_review_times review
     ON target_pr.pr_id = review.pr_id
         LEFT JOIN
     pr_first_commit_times commits
     ON target_pr.pr_id = commits.pr_id
         JOIN
     pull_request_event pre
     ON pre.pull_request_id = target_pr.pr_id and pre.pull_request_event_type = 'OPENED';


BEGIN TRAN


UPDATE pull_request
set lead_time_for_change_process_end = 1
FROM pull_request join pull_request_event on pull_request.pull_request_id = pull_request_event.pull_request_id
where pull_request.lead_time_for_change_process_end = 0
    AND pull_request_event.pull_request_event_type = 'CLOSED'

COMMIT TRAN