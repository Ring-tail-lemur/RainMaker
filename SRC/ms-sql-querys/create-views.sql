CREATE VIEW commits_not_calculated_ltfc AS
SELECT commits.commit_id                               as commit_id,
       commits.release_id                              as release_id,
       target_release.published_at                     as release_time,
       target_release.lead_time_for_change_process_end as lead_time_for_change_process_end
FROM commits
         JOIN (SELECT distinct release.*
               FROM release
                        JOIN release_event
                             ON release.release_id = release_event.release_id
                                 AND release_event.release_event_type = 'PUBLISHED') target_release
              ON target_release.release_id = commits.release_id;
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW commits_first_pr AS
select commit_id, pr_id as first_pr_id, event_time first_pr_close_time
from (select dprct.commit_id                                                   commit_id
           , DENSE_RANK() over (PARTITION BY commit_id order by event_time) AS ranking
           , pr.pull_request_id                                                pr_id
           , event_time
      from pull_request_commit_table dprct
               JOIN pull_request pr
                    ON dprct.pull_request_id = pr.pull_request_id
               JOIN pull_request_event pre
                    ON pr.pull_request_id = pre.pull_request_id
      WHERE pre.pull_request_event_type = 'CLOSED') as dpp
WHERE ranking = 1
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW run_time_error_labels AS
select *,
       substring(issue_label.label, 26, LEN(issue_label.label)) release_name
from issue_label
where left(issue_label.label, 24) = '[RainMaker]runtime-error'
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW closed_issue AS
select release_success_id, event_time as closed_issue_event_time
from release_success
         JOIN issue_event ON release_success.first_error_issue_id = issue_event.issue_id
where issue_event_type = 'CLOSED'
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW opened_issue AS
select release_success_id,
       event_time as opened_issue_event_time,
       time_to_restore_service_process_end,
       repository_id
from release_success
         JOIN issue_event ON release_success.first_error_issue_id = issue_event.issue_id
where issue_event_type = 'OPENED'
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW repository_owner_table AS
select repository_id, repository.name as repository_name, ISNULL(git_user.name, git_organization.name) as owner_name
from repository
         left join git_user ON repository.owner_user_id = git_user_id
         left join git_organization on repository.owner_organization_id = git_organization_id;
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW pr_direction_source_count AS
SELECT prd.outgoing_pull_request_id      AS pr_id,
       count(prd.source_pull_request_id) AS source_count
FROM pull_request_direction prd
GROUP BY prd.outgoing_pull_request_id
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW pr_first_commit_times AS
SELECT pr_commit_table.pull_request_id as pr_id,
             min(c.commit_time) as first_commit_time
      FROM pull_request_commit_table pr_commit_table
               join commits c on pr_commit_table.commit_id = c.commit_id
      GROUP BY pr_commit_table.pull_request_id
------------------------------------------------------------------------------------------------------------------------
CREATE VIEW pr_first_review_times AS
SELECT pull_request_id AS pr_id, MIN(pr_comment.event_time) first_review_time
      FROM pull_request_comment pr_comment
      GROUP BY pull_request_id