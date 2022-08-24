const releaseRepository = require('./releaseRepository');
const labelCreateRestApi = require('../restApiModule/labelCreateRestApi');

async function releaseMain(pool, eventObject, context) {
    const result = await releaseRepository.insertReleaseByUserIdAndTagIdAndRepoId(
        pool, eventObject.X_GitHub_Delivery, eventObject.release_author_id, eventObject.release_tag_name,
        eventObject.repository_id, eventObject.release_id, eventObject.pre_release,
        eventObject.release_name, eventObject.published_at, eventObject.draft, eventObject.action);

    if(result != undefined) {
        try {
            await labelCreateRestApi.createGitHubLabel(eventObject.release_tag_name, eventObject.repository_id, eventObject.repository_name, eventObject.owner_name, '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', context);
            // todo : 중요! 여기에 팀 토큰 정보를 넣어줘야함! 지금은 우리 팀의 정보를 넣었음.
            context.log("태그 생성 완료");
        } catch (e) {
            console.log("태그 생성 중 오류 : ", e);
        }
    }
}

module.exports.releaseMain = releaseMain;