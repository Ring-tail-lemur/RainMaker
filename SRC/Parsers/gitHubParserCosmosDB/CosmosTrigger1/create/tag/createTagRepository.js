async function insertTagByRepoIdAndUserId(dbConnectionPool, tag_name, repository_remote_id, author_id){

    // const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO branch (name, repository_id, git_user_id, create_type)
    VALUES ( '${tag_name}', 
    (
        SELECT repository_id
        FROM repository
        WHERE remote_identifier = ${repository_remote_id}
    ),
    (
        SELECT git_user_id
        FROM git_user
        WHERE remote_identifier = ${author_id}
    ), 'TAG');
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        console.log(e);
    }

}

module.exports.insertTagByRepoIdAndUserId = insertTagByRepoIdAndUserId;

