BEGIN TRAN


INSERT INTO lead_time_for_change
(pull_request_id, first_commit_time, first_review_time, pr_close_time, pr_open_time, repository_id)
SELECT target_pr.pr_id           AS pull_request_id,
       commits.first_commit_time AS first_commit_time,
       review.first_review_time  AS first_review_time,
       target_pr.pr_close_time   AS pr_close_time,
       pre.event_time            AS pr_open_time,
       target_pr.repo_id         as repository_id
FROM (SELECT pr.pull_request_id  AS pr_id,
             pr_event.event_time AS pr_close_time,
             pr.repository_id    as repo_id
      FROM pull_request_event pr_event
               JOIN pull_request pr
                    ON pr_event.pull_request_id = pr.pull_request_id
               LEFT JOIN
           (SELECT prd.outgoing_pull_request_id      AS pr_id,
                   count(prd.source_pull_request_id) AS source_count
            FROM pull_request_direction prd
            WHERE prd.lead_time_for_change_process_end = 0
            GROUP BY prd.outgoing_pull_request_id) pr_source
           ON pr_source.pr_id = pr.pull_request_id
      WHERE pr_event.pull_request_event_type = 'CLOSED'
        AND pr.lead_time_for_change_process_end = 0
        AND pr_source.source_count IS NULL) target_pr
         JOIN
     (SELECT pull_request_id AS pr_id, MIN(pr_comment.event_time) first_review_time
      FROM pull_request_comment pr_comment
      GROUP BY pull_request_id) review
     ON target_pr.pr_id = review.pr_id
         JOIN
     (SELECT pr_commit_table.pull_request_id as pr_id, min(c.commit_time) as first_commit_time
          FROM pull_request_commit_table pr_commit_table
              join commits c on pr_commit_table.commit_id = c.commit_id
          GROUP BY pr_commit_table.pull_request_id) commits
     ON target_pr.pr_id = commits.pr_id
         JOIN
     pull_request_event pre
     ON pre.pull_request_id = target_pr.pr_id and pre.pull_request_event_type = 'OPENED';

UPDATE pull_request
set lead_time_for_change_process_end = 1
where lead_time_for_change_process_end = 0

UPDATE pull_request_direction
set lead_time_for_change_process_end = 1
where lead_time_for_change_process_end = 0

COMMIT TRAN

