package com.ringtaillemur.analyst.query;

//test 3
public class OlapQuery {
	public static final String MAKE_LEAD_TIME_FOR_CHANGE
		=
			"BEGIN TRAN\n"
				+ "\n"
				+ "\t\t\tINSERT INTO lead_time_for_change\n"
				+ "\t\t\t(pull_request_id, first_commit_time, first_review_time, pr_close_time, pr_open_time, repository_id)\n"
				+ "\t\t\tSELECT target_pr.pr_id           AS pull_request_id,\n"
				+ "\t\t\t       commits.first_commit_time AS first_commit_time,\n"
				+ "\t\t\t       review.first_review_time  AS first_review_time,\n"
				+ "\t\t\t       target_pr.pr_close_time   AS pr_close_time,\n"
				+ "\t\t\t       pre.event_time            AS pr_open_time,\n"
				+ "\t\t\t       target_pr.repo_id         as repository_id\n"
				+ "\t\t\tFROM (SELECT pr.pull_request_id  AS pr_id,\n"
				+ "\t\t\t             pr_event.event_time AS pr_close_time,\n"
				+ "\t\t\t             pr.repository_id    as repo_id\n"
				+ "\t\t\t      FROM pull_request_event pr_event\n"
				+ "\t\t\t               JOIN pull_request pr\n"
				+ "\t\t\t                    ON pr_event.pull_request_id = pr.pull_request_id\n"
				+ "\t\t\t               LEFT JOIN\n"
				+ "\t\t\t           (SELECT prd.outgoing_pull_request_id      AS pr_id,\n"
				+ "\t\t\t                   count(prd.source_pull_request_id) AS source_count\n"
				+ "\t\t\t            FROM pull_request_direction prd\n"
				+ "\t\t\t            WHERE prd.lead_time_for_change_process_end = 0\n"
				+ "\t\t\t            GROUP BY prd.outgoing_pull_request_id) pr_source\n"
				+ "\t\t\t           ON pr_source.pr_id = pr.pull_request_id\n"
				+ "\t\t\t      WHERE pr_event.pull_request_event_type = 'CLOSED'\n"
				+ "\t\t\t        AND pr.lead_time_for_change_process_end = 0\n"
				+ "\t\t\t        AND pr_source.source_count IS NULL) target_pr\n"
				+ "\t\t\t         JOIN\n"
				+ "\t\t\t     (SELECT pull_request_id AS pr_id, MIN(pr_comment.event_time) first_review_time\n"
				+ "\t\t\t      FROM pull_request_comment pr_comment\n"
				+ "\t\t\t      GROUP BY pull_request_id) review\n"
				+ "\t\t\t     ON target_pr.pr_id = review.pr_id\n"
				+ "\t\t\t         JOIN\n"
				+ "\t\t\t     (SELECT pr_commit_table.pull_request_id as pr_id, min(c.commit_time) as first_commit_time\n"
				+ "\t\t\t          FROM pull_request_commit_table pr_commit_table\n"
				+ "\t\t\t              join commits c on pr_commit_table.commit_id = c.commit_id\n"
				+ "\t\t\t          GROUP BY pr_commit_table.pull_request_id) commits\n"
				+ "\t\t\t     ON target_pr.pr_id = commits.pr_id\n"
				+ "\t\t\t         JOIN\n"
				+ "\t\t\t     pull_request_event pre\n"
				+ "\t\t\t     ON pre.pull_request_id = target_pr.pr_id and pre.pull_request_event_type = 'OPENED';\n"
				+ "\t\t\t   \n"
				+ "\t\t\tUPDATE pull_request\n"
				+ "\t\t\tset lead_time_for_change_process_end = 1\n"
				+ "\t\t\twhere lead_time_for_change_process_end = 0\n"
				+ "\t\t\t   \n"
				+ "\t\t\tUPDATE pull_request_direction\n"
				+ "\t\t\tset lead_time_for_change_process_end = 1\n"
				+ "\t\t\twhere lead_time_for_change_process_end = 0\n"
				+ "\t\t\t   \n"
				+ "\t\t\tCOMMIT TRAN";
	public static final String MAKE_DEPLOY_TIME
		=
		"UPDATE lead_time_for_change\n"
			+ "\t\t\tSET lead_time_for_change.deployment_time =deployment_time.et,\n"
			+ "\t\t\t\tlead_time_for_change.release_id      =deployment_time.ri\n"
			+ "\t\t\tFROM lead_time_for_change,\n"
			+ "\t\t\t\t(select distinct\n"
			+ "\t\t\t\t\tcommits_first_pr.first_pr_id \t\t\t\tpr_id,\n"
			+ "\t\t\t\t\tcommits_not_calculated_ltfc.published_at \tet,\n"
			+ "\t\t\t\t\tcommits_not_calculated_ltfc.release_id \t\tri\n"
			+ "\t\t\t\tfrom commits_first_pr\n"
			+ "\t\t\t\t\tJOIN commits_not_calculated_ltfc*\n"
			+ "\t\t\t\t\t\tON commits_first_pr.commit_id =commits_not_calculated_ltfc.commit_id\n"
			+ "\t\t\t\tWHERE commits_not_calculated_ltfc.lead_time_for_change_process_end =0) AS deployment_time\n"
			+ "\t\t\twhere deployment_time.pr_id =lead_time_for_change.pull_request_id\n"
			+ "\t\t\t\t\t\t\n"
			+ "\t\t\tUPDATE release\t\n"
			+ "\t\t\tSET lead_time_for_change_process_end = 1\n"
			+ "\t\t\twhere lead_time_for_change_process_end =0;";
	public static final String MAKE_CHANGE_FAILURE_RATE
		="BEGIN TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\tINSERT INTO release_success(release_id, repository_id, released_at)\n"
		+ "\t\t\tSELECT release_id, release.repository_id, release.published_at\n"
		+ "\t\t\tFROM release\n"
		+ "\t\t\tWHERE change_failure_rate_process_end = 0;\n"
		+ "\t\t\t   \n"
		+ "\t\t\tUPDATE release\n"
		+ "\t\t\tSET change_failure_rate_process_end = 1\n"
		+ "\t\t\tWHERE change_failure_rate_process_end = 0;\n"
		+ "\t\t\t   \n"
		+ "\t\t\tCOMMIT TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\t   \n"
		+ "\t\t\tBEGIN TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\tUPDATE release_success\n"
		+ "\t\t\tSET release_success.failed_at = ie.event_time,\n"
		+ "\t\t\t    first_error_issue_id      = ie.issue_id,\n"
		+ "\t\t\t    release_success.is_success= 0\n"
		+ "\t\t\tFROM release_success\n"
		+ "\t\t\t         JOIN run_time_error_labels il ON release_success.repository_id = il.repository_id\n"
		+ "\t\t\t         JOIN release on il.release_name = release.name\n"
		+ "\t\t\t         JOIN issue i ON i.issue_label_id = il.issue_label_id\n"
		+ "\t\t\t         JOIN issue_event ie ON ie.issue_id = i.issue_id\n"
		+ "\t\t\tWHERE release_success.is_success = 1\n"
		+ "\t\t\t  AND i.failed_change_process_end = 0\n"
		+ "\t\t\t  AND ie.issue_event_type = 'OPENED'\n"
		+ "\t\t\t   \n"
		+ "\t\t\tUPDATE issue\n"
		+ "\t\t\tset failed_change_process_end = 1\n"
		+ "\t\t\tFROM issue i\n"
		+ "\t\t\t         JOIN issue_event ie ON ie.issue_id = i.issue_id\n"
		+ "\t\t\twhere failed_change_process_end = 0 AND ie.issue_event_type = 'OPENED'\n"
		+ "\t\t\t   \n"
		+ "\t\t\tCOMMIT TRAN";
	public static final String MAKE_TIME_TO_RESTORE_SERVICE
		="BEGIN TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\tINSERT\n"
		+ "\t\t\tINTO time_to_restore_service (release_success_id, restore_service_time, restored_at, repository_id)\n"
		+ "\t\t\tSELECT closed_issue.release_success_id,\n"
		+ "\t\t\t       DATEDIFF(minute, opened_issue.opened_issue_event_time,\n"
		+ "\t\t\t                closed_issue.closed_issue_event_time) as restore_service_time,\n"
		+ "\t\t\t       closed_issue.closed_issue_event_time,\n"
		+ "\t\t\t       opened_issue.repository_id\n"
		+ "\t\t\tFROM closed_issue\n"
		+ "\t\t\t         join opened_issue\n"
		+ "\t\t\t              on closed_issue.release_success_id = opened_issue.release_success_id\n"
		+ "\t\t\tWHERE time_to_restore_service_process_end = 0;\n"
		+ "\t\t\t   \n"
		+ "\t\t\tCOMMIT TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\tBEGIN TRAN\n"
		+ "\t\t\t   \n"
		+ "\t\t\tUPDATE release_success\n"
		+ "\t\t\tSET release_success.time_to_restore_service_process_end = 1\n"
		+ "\t\t\tFROM issue_event\n"
		+ "\t\t\t         JOIN release_success ON release_success.first_error_issue_id = issue_event.issue_id\n"
		+ "\t\t\tWHERE time_to_restore_service_process_end = 0\n"
		+ "\t\t\t  AND issue_event_type = 'CLOSED'\n"
		+ "\t\t\t   \n"
		+ "\t\t\tCOMMIT TRAN";
	public static String PUBLISHED_AND_NOT_CALCULATE_LEAD_TIME_FOR_CHANGE_RELEASE
		="SELECT release.repository_id as\n"
		+ "\t\t\t           repository_id,\n"
		+ "\t\t\t       release.tag_name      as\n"
		+ "\t\t\t           tag_name,\n"
		+ "\t\t\t       repository_name,\n"
		+ "\t\t\t       owner_name,\n"
		+ "\t\t\t       release.release_id\n"
		+ "\t\t\tFROM release\n"
		+ "\t\t\t         JOIN repository_owner_table\n"
		+ "\t\t\t              on repository_owner_table.repository_id = release.repository_id\n"
		+ "\t\t\t         JOIN release_event\n"
		+ "\t\t\t              ON release.release_id = release_event.release_id\n"
		+ "\t\t\t                  AND release_event.release_event_type = 'PUBLISHED'\n"
		+ "\t\t\t         JOIN repository\n"
		+ "\t\t\t              ON release.repository_id = repository.repository_id\n"
		+ "\t\t\tWHERE release.lead_time_for_change_process_end = 0\n"
		+ "\t\t\tORDER BY release.published_at;";
	public static String PUBLISHED_AND_CALCULATED_LEAD_TIME_FOR_CHANGE_RELEASE
		="SELECT TOP 1 release.repository_id,\n"
		+ "\t\t\t             release.tag_name,\n"
		+ "\t\t\t             repository_name,\n"
		+ "\t\t\t             owner_name,\n"
		+ "\t\t\t             release.release_id\n"
		+ "\t\t\tFROM release\n"
		+ "\t\t\t         JOIN repository_owner_table\n"
		+ "\t\t\t              on repository_owner_table.repository_id = release.repository_id\n"
		+ "\t\t\t         JOIN release_event\n"
		+ "\t\t\t              ON release.release_id = release_event.release_id\n"
		+ "\t\t\t                  AND release_event.release_event_type = 'PUBLISHED'\n"
		+ "\t\t\t         JOIN repository\n"
		+ "\t\t\t              ON release.repository_id = repository.repository_id\n"
		+ "\t\t\tWHERE release.lead_time_for_change_process_end = 1\n"
		+ "\t\t\tORDER BY release.published_at;";
	public static String UPDATE_COMMITS_RELEASE_ID
		="MERGE commits\n"
		+ "USING(VALUES %s) as pairs(sha,changed_release_id)\n"
		+ "\ton commit_id = sha\n"
		+ "WHEN MATCHED\n"
		+ "THEN UPDATE\n"
		+ "SET release_id = changed_release_id;";
}
