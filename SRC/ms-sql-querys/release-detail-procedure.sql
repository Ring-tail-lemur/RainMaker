CREATE procedure releaseDetail (@repoString VARCHAR(MAX), @startTime DATETIME2, @endTime DATETIME2)
AS
BEGIN
    SELECT distinct r.release_id,
                    r.tag_name,
                    r.published_at,
                    r.name                                                               AS release_name,
                    count(c.commit_id) OVER (PARTITION BY c.release_id)                  AS commit_size,
                    repository.name                                                      AS repository_name,
                    ISNULL(sum(ltfcccs.code_change) OVER (PARTITION BY c.release_id), 0) AS code_change_size
    FROM release r
             LEFT JOIN commits c on r.release_id = c.release_id
             JOIN repository on r.repository_id = repository.repository_id
             LEFT JOIN pull_request_commit_table prct on c.commit_id = prct.commit_id
             LEFT JOIN lead_time_for_change_code_change_size ltfcccs on prct.pull_request_id = ltfcccs.pull_request_id
    WHERE r.repository_id IN (SELECT value FROM STRING_SPLIT(TRIM(']' FROM TRIM('[' FROM @repoString)), ','))
    AND published_at BETWEEN @startTime and @endTime
end
go

