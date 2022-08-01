select target_pr.pr_id           as pull_request_id,
       commits.first_commit_time as first_commit_time,
       review.first_review_time  as first_review_time,
       target_pr.pr_close_time   as pr_close_time
from (select pr.pull_request_id  as pr_id,
             pr_event.event_time as pr_close_time
      from pull_request_event pr_event
               join pull_request pr
                    on pr_event.pull_request_id = pr.pull_request_id
               LEFT JOIN (select prd.outgoing_pull_request_id      as pr_id,
                                 count(prd.source_pull_request_id) as source_count
                          from pull_request_direction prd
                          group by prd.outgoing_pull_request_id) pr_source
                         on pr_source.pr_id = pr.pull_request_id
      where pr_event.event_type = 'CLOSED'
        AND pr.process_end = 0
        AND pr_source.source_count is null) target_pr
         join (select pull_request_id as pr_id, MIN(pr_comment.event_time) first_review_time
               from pull_request_comment pr_comment
               group by pull_request_id) review on target_pr.pr_id = review.pr_id
         join (select pr_commit_table.pull_request_id as pr_id, c.commit_time as first_commit_time
               from pull_request_commit_table pr_commit_table
                        join [commit] c
                             on pr_commit_table.commit_id = c.commit_id
               where pr_commit_table.first_commit = 1) commits on target_pr.pr_id = commits.pr_id
