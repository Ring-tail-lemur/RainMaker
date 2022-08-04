BEGIN TRAN


update lead_time_for_change
set lead_time_for_change.deployment_time     = deployment_time.et,
    lead_time_for_change.deployment_event_id = deployment_time.de_id
FROM lead_time_for_change,
     (select distinct my_table.pr_id pr_id,
                      my_table.event_time et,
                      target_commit.deployment_event_id de_id
      from (select dprct.commit_id                                                   commit_id
                 , DENSE_RANK() over (PARTITION BY commit_id order by event_time) as ranking
                 , pr.pull_request_id                                                pr_id
                 , event_time
            from pull_request_commit_table dprct
                     join pull_request pr
                          on dprct.pull_request_id = pr.pull_request_id
                     join pull_request_event pre
                          on pr.pull_request_id = pre.pull_request_id
            where pre.event_type = 'CLOSED') my_table
               join (select prct.commit_id as commit_id,
                            de.deployment_event_id
                     from deployment_event de
                              join pull_request_commit_table prct
                                   on de.pull_request_id = prct.pull_request_id
                     where de.process_end = 0) target_commit
                    on my_table.commit_id = target_commit.commit_id
      where ranking = 1) as deployment_time
where deployment_time.pr_id = lead_time_for_change.lead_time_for_change_id


-- UPDATE pull_request
-- set process_end = 1
-- where process_end = 0


COMMIT TRAN

