WITH COMMIT_ID_MIN_TIME AS (SELECT DISTINCT commit_id,
                                            MIN(PR.created_date) OVER (PARTITION BY PRCT.commit_id) AS MIN_TIME
                            FROM pull_request_commit_table PRCT
                                     JOIN pull_request PR
                                          ON PR.pull_request_id = PRCT.pull_request_id
                            WHERE commit_id IN (SELECT commit_id
                                                FROM pull_request_commit_table
                                                WHERE pull_request_id NOT IN (SELECT source_pull_request_id
                                                                              FROM pull_request_direction)
                                                GROUP BY commit_id
                                                HAVING count(commit_id) >= 2)),
     SOURCE AS (SELECT distinct PR.pull_request_id, PRCT.commit_id
                FROM pull_request_commit_table PRCT
                         JOIN pull_request PR
                              ON PR.pull_request_id = PRCT.pull_request_id
                         JOIN (SELECT * FROM COMMIT_ID_MIN_TIME) temp
                              ON temp.commit_id = PRCT.commit_id AND temp.MIN_TIME = PR.created_date
                WHERE PR.pull_request_id NOT IN (SELECT source_pull_request_id
                                                 FROM pull_request_direction)),

     SECOND_TIME AS (SELECT distinct PRCT.commit_id,
                                     MIN(PR.created_date) OVER (PARTITION BY PRCT.commit_id) AS SECOND_MIN_TIME
                     FROM pull_request_commit_table PRCT
                              JOIN pull_request PR
                                   ON PR.pull_request_id = PRCT.pull_request_id
                              JOIN (SELECT * FROM COMMIT_ID_MIN_TIME) temp
                                   ON temp.commit_id = PRCT.commit_id AND temp.MIN_TIME < PR.created_date),

     OUTGOING AS (SELECT distinct PR.pull_request_id, PRCT.commit_id
                  FROM pull_request_commit_table PRCT
                           JOIN pull_request PR
                                ON PR.pull_request_id = PRCT.pull_request_id
                           JOIN SECOND_TIME ST
                                ON ST.commit_id = PRCT.commit_id
                                    AND ST.SECOND_MIN_TIME = PR.created_date)
INSERT
INTO pull_request_direction (source_pull_request_id, outgoing_pull_request_id)
OUTPUT inserted.source_pull_request_id, inserted.outgoing_pull_request_id
SELECT DISTINCT S.pull_request_id, O.pull_request_id
FROM OUTGOING O
         JOIN SOURCE S
              ON O.commit_id = S.commit_id