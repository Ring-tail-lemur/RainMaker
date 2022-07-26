package com.ringtaillemur.analyst.query;

//test 4
public class OlapQuery {
	public static final String MAKE_LEAD_TIME_FOR_CHANGE
		=
		"INSERT INTO lead_time_for_change\n"
			+ "(pull_request_id, first_commit_time, pr_open_time, first_review_time, pr_close_time, repository_id)\n"
			+ "SELECT target_pr.pr_id           AS pull_request_id,\n"
			+ "       isnull(commits.first_commit_time, pre.event_time) AS first_commit_time,\n"
			+ "       pre.event_time            AS pr_open_time,\n"
			+ "       isnull(review.first_review_time, pr_close_time)  AS first_review_time,\n"
			+ "       target_pr.pr_close_time   AS pr_close_time,\n"
			+ "       target_pr.repo_id         as repository_id\n"
			+ "FROM (SELECT pr.pull_request_id  AS pr_id,\n"
			+ "             pr_event.event_time AS pr_close_time,\n"
			+ "             pr.repository_id    as repo_id\n"
			+ "      FROM pull_request_event pr_event\n"
			+ "               JOIN pull_request pr\n"
			+ "                    ON pr_event.pull_request_id = pr.pull_request_id\n"
			+ "               LEFT JOIN\n"
			+ "           pr_direction_source_count pr_source\n"
			+ "           ON pr_source.pr_id = pr.pull_request_id\n"
			+ "      WHERE pr_event.pull_request_event_type = 'CLOSED'\n"
			+ "        AND pr.lead_time_for_change_process_end = 0\n"
			+ "        AND pr_source.source_count IS NULL) target_pr\n"
			+ "         LEFT JOIN\n"
			+ "     pr_first_review_times review\n"
			+ "     ON target_pr.pr_id = review.pr_id\n"
			+ "         LEFT JOIN\n"
			+ "     pr_first_commit_times commits\n"
			+ "     ON target_pr.pr_id = commits.pr_id\n"
			+ "         JOIN\n"
			+ "     pull_request_event pre\n"
			+ "     ON pre.pull_request_id = target_pr.pr_id and pre.pull_request_event_type = 'OPENED';\n"
			+ "\n"
			+ "\n"
			+ "BEGIN TRAN\n"
			+ "\n"
			+ "\n"
			+ "UPDATE pull_request\n"
			+ "set lead_time_for_change_process_end = 1\n"
			+ "FROM pull_request join pull_request_event on pull_request.pull_request_id = pull_request_event.pull_request_id\n"
			+ "where pull_request.lead_time_for_change_process_end = 0\n"
			+ "    AND pull_request_event.pull_request_event_type = 'CLOSED'\n"
			+ "\n"
			+ "COMMIT TRAN";
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
			+ "\t\t\t\t\tJOIN commits_not_calculated_ltfc\n"
			+ "\t\t\t\t\t\tON commits_first_pr.commit_id =commits_not_calculated_ltfc.commit_id\n"
			+ "\t\t\t\tWHERE commits_not_calculated_ltfc.lead_time_for_change_process_end =0) AS deployment_time\n"
			+ "\t\t\twhere deployment_time.pr_id =lead_time_for_change.pull_request_id\n"
			+ "\t\t\t\t\t\t\n"
			+ "\t\t\tUPDATE release\t\n"
			+ "\t\t\tSET lead_time_for_change_process_end = 1\n"
			+ "\t\t\twhere lead_time_for_change_process_end =0;";
	public static final String MAKE_CHANGE_FAILURE_RATE
		= "BEGIN TRAN\n"
		+ "\n"
		+ "INSERT INTO release_success(release_id, repository_id, released_at)\n"
		+ "SELECT release_id, release.repository_id, release.published_at\n"
		+ "FROM release\n"
		+ "WHERE change_failure_rate_process_end = 0;\n"
		+ "\n"
		+ "UPDATE release\n"
		+ "SET change_failure_rate_process_end = 1\n"
		+ "WHERE change_failure_rate_process_end = 0;\n"
		+ "\n"
		+ "UPDATE release_success\n"
		+ "SET release_success.failed_at = ie.event_time,\n"
		+ "    first_error_issue_id      = ie.issue_id,\n"
		+ "    release_success.is_success= 0\n"
		+ "FROM release\n"
		+ "         JOIN run_time_error_labels il on release.name = il.release_name\n"
		+ "         JOIN release_success\n"
		+ "              ON release_success.release_id = release.release_id\n"
		+ "         JOIN issue i ON i.issue_label_id = il.issue_label_id\n"
		+ "         JOIN issue_event ie ON ie.issue_id = i.issue_id\n"
		+ "WHERE ie.issue_event_type = 'OPENED'\n"
		+ "  AND i.failed_change_process_end = 0\n"
		+ "  AND release_success.is_success = 1;\n"
		+ "COMMIT TRAN\n"
		+ "\n"
		+ "BEGIN TRAN\n"
		+ "\n"
		+ "UPDATE issue\n"
		+ "SET failed_change_process_end = 1\n"
		+ "FROM issue i\n"
		+ "         JOIN issue_event ie ON ie.issue_id = i.issue_id\n"
		+ "where failed_change_process_end = 0\n"
		+ "  AND ie.issue_event_type = 'OPENED';\n"
		+ "\n"
		+ "COMMIT TRAN\n";
	public static final String MAKE_TIME_TO_RESTORE_SERVICE
		= "BEGIN TRAN\n"
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
		= "SELECT release.repository_id as\n"
		+ "\t\t\t           repository_id,\n"
		+ "\t\t\t       release.tag_name      as\n"
		+ "\t\t\t           tag_name,\n"
		+ "\t\t\t       repository_name,\n"
		+ "\t\t\t       owner_name,\n"
		+ "\t\t\t       release.release_id\n"
		+ "\t\t\tFROM release\n"
		+ "\t\t\t         JOIN repository_owner_table\n"
		+ "\t\t\t              on repository_owner_table.repository_id = release.repository_id\n"
		+ "\t\t\tWHERE release.lead_time_for_change_process_end = 0\n"
		+ "\t\t\tORDER BY release.repository_id, release.published_at;";
	public static String PUBLISHED_AND_CALCULATED_LEAD_TIME_FOR_CHANGE_RELEASE
		= "SELECT TOP 1 release.repository_id,\n" +
			"\t\t\t             release.tag_name,\n" +
			"\t\t\t             repository_name,\n" +
			"\t\t\t             owner_name,\n" +
			"\t\t\t             release.release_id\n" +
			"\t\t\tFROM release\n" +
			"\t\t\t         JOIN repository_owner_table\n" +
			"\t\t\t              on repository_owner_table.repository_id = release.repository_id\n" +
			"\t\t\t         JOIN repository\n" +
			"\t\t\t              ON release.repository_id = repository.repository_id\n" +
			"\t\t\tWHERE release.lead_time_for_change_process_end = 1\n" +
			"\t\t\tAND release.repository_id = %s\n" +
			"\t\t\tORDER BY release.published_at;";
	public static String UPDATE_COMMITS_RELEASE_ID
		= "MERGE commits\n"
		+ "USING(VALUES %s) as pairs(sha,changed_release_id)\n"
		+ "\ton commit_id = sha\n"
		+ "WHEN MATCHED\n"
		+ "THEN UPDATE\n"
		+ "SET commits.release_id = pairs.changed_release_id;";
	public static String MAKE_PULL_REQUEST_DIRECTION
		= "WITH COMMIT_ID_MIN_TIME AS (SELECT DISTINCT commit_id,\n" +
		"                                            MIN(PR.pull_request_number) OVER (PARTITION BY PRCT.commit_id) AS MIN_TIME\n" +
		"                            FROM pull_request_commit_table PRCT\n" +
		"                                     JOIN pull_request PR\n" +
		"                                          ON PR.pull_request_id = PRCT.pull_request_id\n" +
		"                            WHERE commit_id IN (SELECT commit_id\n" +
		"                                                FROM pull_request_commit_table\n" +
		"                                                WHERE pull_request_id NOT IN (SELECT source_pull_request_id\n" +
		"                                                                              FROM pull_request_direction)\n" +
		"                                                GROUP BY commit_id\n" +
		"                                                HAVING count(commit_id) >= 2)),\n" +
		"     SOURCE AS (SELECT distinct PR.pull_request_id, PRCT.commit_id\n" +
		"                FROM pull_request_commit_table PRCT\n" +
		"                         JOIN pull_request PR\n" +
		"                              ON PR.pull_request_id = PRCT.pull_request_id\n" +
		"                         JOIN (SELECT * FROM COMMIT_ID_MIN_TIME) temp\n" +
		"                              ON temp.commit_id = PRCT.commit_id AND temp.MIN_TIME = PR.pull_request_number\n" +
		"                WHERE PR.pull_request_id NOT IN (SELECT source_pull_request_id\n" +
		"                                                 FROM pull_request_direction)),\n" +
		"\n" +
		"     SECOND_TIME AS (SELECT distinct PRCT.commit_id,\n" +
		"                                     MIN(PR.pull_request_number) OVER (PARTITION BY PRCT.commit_id) AS SECOND_MIN_TIME\n" +
		"                     FROM pull_request_commit_table PRCT\n" +
		"                              JOIN pull_request PR\n" +
		"                                   ON PR.pull_request_id = PRCT.pull_request_id\n" +
		"                              JOIN (SELECT * FROM COMMIT_ID_MIN_TIME) temp\n" +
		"                                   ON temp.commit_id = PRCT.commit_id AND temp.MIN_TIME < PR.pull_request_number),\n" +
		"\n" +
		"     OUTGOING AS (SELECT distinct PR.pull_request_id, PRCT.commit_id\n" +
		"                  FROM pull_request_commit_table PRCT\n" +
		"                           JOIN pull_request PR\n" +
		"                                ON PR.pull_request_id = PRCT.pull_request_id\n" +
		"                           JOIN SECOND_TIME ST\n" +
		"                                ON ST.commit_id = PRCT.commit_id\n" +
		"                                    AND ST.SECOND_MIN_TIME = PR.pull_request_number)\n" +
		"INSERT\n" +
		"INTO pull_request_direction (source_pull_request_id, outgoing_pull_request_id)\n" +
		"OUTPUT inserted.source_pull_request_id, inserted.outgoing_pull_request_id\n" +
		"SELECT DISTINCT S.pull_request_id, O.pull_request_id\n" +
		"FROM OUTGOING O\n" +
		"         JOIN SOURCE S\n" +
		"              ON O.commit_id = S.commit_id";
}
