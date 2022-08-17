const createTagRepository = require('./createTagRepository');

async function createTagMain(pool, eventObject) {
    await createTagRepository.insertTagByRepoIdAndUserId(pool, eventObject.ref_name, eventObject.repository_id, eventObject.owner_id);
}

module.exports.createTagMain = createTagMain;