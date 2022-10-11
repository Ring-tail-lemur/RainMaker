const errLogModule = require('../../utils/slackLogBot.js');
async function insertTagByRepoIdAndUserId(dbConnectionPool, tag_id, tag_name, repository_remote_id, author_id){

    // const dbConnectionPool = await pool;

    const sqlQuery = `
    INSERT INTO branch (branch_id, name, repository_id, git_user_id, create_type)
    VALUES ( '${tag_id}', '${tag_name}', ${repository_remote_id}, ${author_id}, 'TAG');
    `;
    console.log(sqlQuery);

    try {
        await dbConnectionPool.request()
            .query(sqlQuery);
    } catch (e) {
        errLogModule.log(e, "createTagRepository.js // insertTagByRepoIdAndUserId");
        console.log(e);
    }

}

module.exports.insertTagByRepoIdAndUserId = insertTagByRepoIdAndUserId;

