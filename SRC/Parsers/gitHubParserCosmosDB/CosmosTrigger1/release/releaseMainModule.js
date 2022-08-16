const releaseRepository = require('./releaseRepository');

async function releaseMain(pool, eventObject, context) {
    await releaseRepository.insertReleaseByUserIdAndTagIdAndRepoId(
        pool, eventObject.release_author_id, eventObject.release_tag_name,
        eventObject.repository_id, eventObject.release_id, eventObject.pre_release,
        eventObject.release_name, eventObject.published_at, eventObject.draft, eventObject.action);
}

module.exports.releaseMain = releaseMain;