const releaseRepository = require('./releaseRepository');
const labelCreateRestApi = require('../restApiModule/labelCreateRestApi');

async function releaseMain(pool, eventObject, context) {

    context.log(eventObject.action, "1");
    if(eventObject.action === "created") {
        await releaseRepository.insertReleaseByUserIdAndTagIdAndRepoId(pool, context, eventObject.X_GitHub_Delivery, eventObject.release_author_id, eventObject.release_tag_name, eventObject.repository_id, eventObject.release_id, eventObject.pre_release, eventObject.release_name, eventObject.published_at, eventObject.draft, eventObject.action);
    } else if (eventObject.action === "deleted") {
        context.log(eventObject.action, "2");
        await releaseRepository.deleteReleaseByReleaseId(pool, context, eventObject.release_id);
    }

}

module.exports.releaseMain = releaseMain;