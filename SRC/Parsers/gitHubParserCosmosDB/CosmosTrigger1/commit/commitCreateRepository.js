const pool = require('../ms-sql/msSQLPool');

async function insertCommitByUserId(commit_sha, author_id, message) {
    console.log("HI");
}

module.exports.insertCommitByUserId = insertCommitByUserId;