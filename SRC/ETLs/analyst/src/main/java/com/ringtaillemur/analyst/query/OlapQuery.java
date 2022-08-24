package com.ringtaillemur.analyst.query;

//test 3
public class OlapQuery {
	public static final String MAKE_LEAD_TIME_FOR_CHANGE
		=
		"""
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
			   
						
			""";
	public static final String MAKE_DEPLOY_TIME
		=
		"""
			UPDATE lead_time_for_change
			SET lead_time_for_change.deployment_time =deployment_time.et,
				lead_time_for_change.release_id      =deployment_time.ri
			FROM lead_time_for_change,
				(select distinct
					commits_first_pr.first_pr_id 				pr_id,
					commits_not_calculated_ltfc.published_at 	et,
					commits_not_calculated_ltfc.release_id 		ri
				from commits_first_pr
					JOIN commits_not_calculated_ltfc*
						ON commits_first_pr.commit_id =commits_not_calculated_ltfc.commit_id
				WHERE commits_not_calculated_ltfc.lead_time_for_change_process_end =0) AS deployment_time
			where deployment_time.pr_id =lead_time_for_change.pull_request_id
						
			UPDATE release	
			SET lead_time_for_change_process_end = 1
			where lead_time_for_change_process_end =0;
			""";
	public static final String MAKE_CHANGE_FAILURE_RATE
		=
		"""
			BEGIN TRAN
			   
			INSERT INTO release_success(release_id, repository_id, released_at)
			SELECT release_id, release.repository_id, release.published_at
			FROM release
			WHERE change_failure_rate_process_end = 0;
			   
			UPDATE release
			SET change_failure_rate_process_end = 1
			WHERE change_failure_rate_process_end = 0;
			   
			COMMIT TRAN
			   
			   
			BEGIN TRAN
			   
			UPDATE release_success
			SET release_success.failed_at = ie.event_time,
			    first_error_issue_id      = ie.issue_id,
			    release_success.is_success= 0
			FROM release_success
			         JOIN run_time_error_labels il ON release_success.repository_id = il.repository_id
			         JOIN release on il.release_name = release.name
			         JOIN issue i ON i.issue_label_id = il.issue_label_id
			         JOIN issue_event ie ON ie.issue_id = i.issue_id
			WHERE release_success.is_success = 1
			  AND i.failed_change_process_end = 0
			  AND ie.issue_event_type = 'OPENED'
			   
			UPDATE issue
			set failed_change_process_end = 1
			FROM issue i
			         JOIN issue_event ie ON ie.issue_id = i.issue_id
			where failed_change_process_end = 0 AND ie.issue_event_type = 'OPENED'
			   
			COMMIT TRAN
			""";
	public static final String MAKE_TIME_TO_RESTORE_SERVICE
		=
		"""
			BEGIN TRAN
			   
			INSERT
			INTO time_to_restore_service (release_success_id, restore_service_time, restored_at, repository_id)
			SELECT closed_issue.release_success_id,
			       DATEDIFF(minute, opened_issue.opened_issue_event_time,
			                closed_issue.closed_issue_event_time) as restore_service_time,
			       closed_issue.closed_issue_event_time,
			       opened_issue.repository_id
			FROM closed_issue
			         join opened_issue
			              on closed_issue.release_success_id = opened_issue.release_success_id
			WHERE time_to_restore_service_process_end = 0;
			   
			COMMIT TRAN
			   
			BEGIN TRAN
			   
			UPDATE release_success
			SET release_success.time_to_restore_service_process_end = 1
			FROM issue_event
			         JOIN release_success ON release_success.first_error_issue_id = issue_event.issue_id
			WHERE time_to_restore_service_process_end = 0
			  AND issue_event_type = 'CLOSED'
			   
			COMMIT TRAN
			""";
	public static String PUBLISHED_AND_NOT_CALCULATE_LEAD_TIME_FOR_CHANGE_RELEASE
		=
		"""
			SELECT release.repository_id as
			           repository_id,
			       release.tag_name      as
			           tag_name,
			       repository_name,
			       owner_name,
			       release.release_id
			FROM release
			         JOIN repository_owner_table
			              on repository_owner_table.repository_id = release.repository_id
			         JOIN release_event
			              ON release.release_id = release_event.release_id
			                  AND release_event.release_event_type = 'PUBLISHED'
			         JOIN repository
			              ON release.repository_id = repository.repository_id
			WHERE release.lead_time_for_change_process_end = 0
			ORDER BY release.published_at;
			""";
	public static String PUBLISHED_AND_CALCULATED_LEAD_TIME_FOR_CHANGE_RELEASE
		=
		"""
			SELECT TOP 1 release.repository_id,
			             release.tag_name,
			             repository_name,
			             owner_name,
			             release.release_id
			FROM release
			         JOIN repository_owner_table
			              on repository_owner_table.repository_id = release.repository_id
			         JOIN release_event
			              ON release.release_id = release_event.release_id
			                  AND release_event.release_event_type = 'PUBLISHED'
			         JOIN repository
			              ON release.repository_id = repository.repository_id
			WHERE release.lead_time_for_change_process_end = 1
			ORDER BY release.published_at;
			""";
	public static String UPDATE_COMMITS_RELEASE_ID
		=
		"""
						MERGE commits
			USING(VALUES %s) as pairs(sha,changed_release_id)
				on commit_id = sha
			WHEN MATCHED
			THEN UPDATE
			SET release_id = changed_release_id;
			""";
}
