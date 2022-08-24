UPDATE lead_time_for_change
SET lead_time_for_change.deployment_time = deployment_time.et,
    lead_time_for_change.release_id      = deployment_time.ri
FROM lead_time_for_change,
     (select distinct commits_first_pr.first_pr_id      pr_id,
                      commits_not_calculated_ltfc.published_at et,
                      commits_not_calculated_ltfc.release_id   ri
      from commits_first_pr
               JOIN commits_not_calculated_ltfc
                    ON commits_first_pr.commit_id = commits_not_calculated_ltfc.commit_id
      WHERE commits_not_calculated_ltfc.lead_time_for_change_process_end = 0) AS deployment_time
where deployment_time.pr_id = lead_time_for_change.pull_request_id


UPDATE release
SET lead_time_for_change_process_end = 1
where lead_time_for_change_process_end = 0