WITH target_release AS (SELECT distinct release.release_id
                                      , release.published_at
                        FROM release
                                 JOIN release_event ON release.release_id = release_event.release_id
                        WHERE release.lead_time_for_change_process_end = 0),
     target_commits AS
         (SELECT commits.commit_id, commits.release_id, target_release.published_at
          FROM commits
                   JOIN target_release ON target_release.release_id = commits.release_id),
     commits_ranked_by_event_time AS
         (select dprct.commit_id                                                   commit_id
               , DENSE_RANK() over (PARTITION BY commit_id order by event_time) AS ranking
               , pr.pull_request_id                                                pr_id
               , event_time
          from pull_request_commit_table dprct
                   JOIN pull_request pr
                        ON dprct.pull_request_id = pr.pull_request_id
                   JOIN pull_request_event pre
                        ON pr.pull_request_id = pre.pull_request_id
          WHERE pre.pull_request_event_type = 'CLOSED'),
     commits_first_pr AS (select commit_id, pr_id, event_time
                          from commits_ranked_by_event_time
                          WHERE ranking = 1)

UPDATE lead_time_for_change

SET lead_time_for_change.deployment_time = deployment_time.et,
    lead_time_for_change.release_id      = deployment_time.ri
FROM lead_time_for_change,
     (select distinct commits_first_pr.pr_id      pr_id,
                      target_commits.published_at et,
                      target_commits.release_id   ri
      from commits_first_pr
               JOIN target_commits
                    ON commits_first_pr.commit_id = target_commits.commit_id) AS deployment_time
where deployment_time.pr_id = lead_time_for_change.pull_request_id


UPDATE release
SET lead_time_for_change_process_end = 1
where lead_time_for_change_process_end = 0