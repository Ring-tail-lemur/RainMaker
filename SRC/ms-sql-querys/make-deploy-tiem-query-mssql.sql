BEGIN TRAN


with target_release as
         (SELECT release.release_id
          FROM release
                   JOIN release_event on release.release_id = release_event.release_id
          where release.process_end = 0),
     target_commits as
         (SELECT commits.commit_id, commits.release_id
          FROM commits
                   JOIN target_release on target_release.release_id = commits.release_id),
     commits_ranked_by_event_time as
         (select dprct.commit_id                                                   commit_id
               , DENSE_RANK() over (PARTITION BY commit_id order by event_time) as ranking
               , pr.pull_request_id                                                pr_id
               , event_time
          from pull_request_commit_table dprct
                   join pull_request pr
                        on dprct.pull_request_id = pr.pull_request_id
                   join pull_request_event pre
                        on pr.pull_request_id = pre.pull_request_id
          where pre.pull_request_event_type = 'CLOSED'),
     commits_first_pr as (select commit_id, pr_id, event_time
                          from commits_ranked_by_event_time
                          where ranking = 1)

update lead_time_for_change
set lead_time_for_change.deployment_time = deployment_time.et,
    lead_time_for_change.release_id      = deployment_time.ri
FROM lead_time_for_change,
     (select distinct commits_first_pr.pr_id      pr_id,
                      commits_first_pr.event_time et,
                      target_commits.release_id   ri
      from commits_first_pr
               join target_commits
                    on commits_first_pr.commit_id = target_commits.commit_id) as deployment_time
where deployment_time.pr_id = lead_time_for_change.pull_request_id


UPDATE pull_request
set process_end = 1
where process_end = 0


COMMIT TRAN


