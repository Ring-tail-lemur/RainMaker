BEGIN TRAN

UPDATE deployment_event SET process_end = true WHERE process_end = false;
SELECT
FROM
WHERE

COMMIT TRAN