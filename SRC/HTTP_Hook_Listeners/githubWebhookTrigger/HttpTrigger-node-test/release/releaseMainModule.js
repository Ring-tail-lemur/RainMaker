const createdModule = require('./createdModule.js');
const timeModule = require('../utils/getCurrentTimeModule.js');
const labelCreateModule = require('../utils/labelCreateRestApi.js');
const msSQLModule = require('../ms-sql/msSQLModule.js');
async function releaseMain(hookBody, cloudEventObj, context) {
    try{
        cloudEventObj.action = JSON.stringify(hookBody.action).replace(/['"]+/g, '');
        cloudEventObj.release_id = JSON.stringify(hookBody.release.id).replace(/['"]+/g, '');
        cloudEventObj.release_url = JSON.stringify(hookBody.release.url).replace(/['"]+/g, '');
        cloudEventObj.release_author_id = JSON.stringify(hookBody.release.author.id).replace(/['"]+/g, '');
        cloudEventObj.release_tag_name = JSON.stringify(hookBody.release.tag_name).replace(/['"]+/g, '');
        cloudEventObj.release_name = JSON.stringify(hookBody.release.name).replace(/['"]+/g, '');
        cloudEventObj.draft = JSON.stringify(hookBody.release.draft).replace(/['"]+/g, '');
        cloudEventObj.published_at = JSON.stringify(hookBody.release.published_at).replace(/['"]+/g, '');
        cloudEventObj.pre_release = JSON.stringify(hookBody.release.prerelease).replace(/['"]+/g, '');
        cloudEventObj.repository_id = JSON.stringify(hookBody.repository.id).replace(/['"]+/g, '');
        cloudEventObj.repository_name = JSON.stringify(hookBody.repository.name).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_id =  JSON.stringify(hookBody.repository.owner.id).replace(/['"]+/g, '');
        cloudEventObj.repository_private = JSON.stringify(hookBody.repository.private).replace(/['"]+/g, '');
        cloudEventObj.repository_owner_type = JSON.stringify(hookBody.repository.owner.type).replace(/['"]+/g, '');
        cloudEventObj.owner_name = JSON.stringify(hookBody.repository.owner.login).replace(/['"]+/g, '');
        cloudEventObj.event_time = await timeModule.getCurrentTime();
        const accessToken = await msSQLModule.getTokenByRepositoryId(cloudEventObj.repository_id);
        await labelCreateModule.createGitHubLabel(cloudEventObj.release_name, cloudEventObj.repository_id
            , cloudEventObj.repository_name, cloudEventObj.owner_name, accessToken ,context);
        return cloudEventObj;
    }catch(err){
        context.log(cloudEventObj.action + " is not yet prepared");
    }
    

}
module.exports.releaseMain = releaseMain;