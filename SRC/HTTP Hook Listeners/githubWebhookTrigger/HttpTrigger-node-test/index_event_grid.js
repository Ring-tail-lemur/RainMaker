const send = require("./send.js");
const send_post = require("./send_post.js");

module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');
    const cloudEventObj = new Object();
    const obj = req.body;
    const obj_header = req.headers;

    // header dup 방지를 위한 hook-id 수집 
    cloudEventObj.hook_id = JSON.stringify(obj_header["x-github-hook-id"]).replace(/['"]+/g, '');

    // .replace(/['"]+/g, '') <- double quote problem solve (e.g. "\"hi\"")
    // 공통 부분 작성 (어디에서 왔는지 / 무슨 행동인지 / 행위자는 누구인지 / Organization id(이름 추가 가능) / repository id(이름도 추가 가능) / data_content_type )
    cloudEventObj.source = 'github'.replace(/['"]+/g, '');

    try{
        cloudEventObj.action = JSON.stringify(obj.action).replace(/['"]+/g, '');
        cloudEventObj.actor_id = JSON.stringify(obj.sender.id);
        cloudEventObj.org_id = JSON.stringify(obj.organization.id);
        cloudEventObj.repo_id = JSON.stringify(obj.repository.id);
    }catch(e){
        //push일 경우가 생김
        try{
            const pusher = JSON.stringify(obj.pusher.name);
            if(pusher != null || pusher != 'null' || pusher != 'undefined'){
                context.log("pushed");
                cloudEventObj.action = 'pushed'
            }
        }catch(e){
            const response = "undefined action triggered."
            cloudEventObj.resHeader = response;

            context.res = {
                // status: 200, /* Defaults to 200 */
                body: cloudEventObj
            };
        }
    }   

    cloudEventObj.data_content_type = '"application/json"'.replace(/['"]+/g, '');

    // 이 부분은 Event 종류별로 객체를 가져올 수 있는 것이 다르기 때문에, 분기문으로 작성했다. 성의없이 하드코딩하는게 미안하긴 한데.... 어쩔 수 없다고 생각한다. 견뎌라.
    if(cloudEventObj.action == 'created'){
        // comment 발생 시
        // context.log(JSON.stringify(obj.issue));
        // action의 타입(created라고 하면 못 알아 듣는다.) / action_time(comment를 단 시간) / pr_url(pr이 달린 시각))
        cloudEventObj.action_type = 'comment';

        cloudEventObj.action_time = JSON.stringify(obj.comment.created_at).replace(/['"]+/g, '');

        cloudEventObj.pr_url = JSON.stringify(obj.issue.pull_request.url).replace(/['"]+/g, '');

    }else if(cloudEventObj.action == 'submitted'){
        // code review  발생 시
        // action의 타입(코드 리뷰를 뜻한다.) / 발생 시각 / review가 있는 pr의 url / review가 있는 pr의 id / review가 달린 commit의 id / review의 결과(comment/approve/request change)
        // approve 옵션을 할 경우, submit이 두번 요청됨.(state를 추가한다.)
            // Comment : 승인과 무관하게 일반적인 커멘트를 할 때 선택한다.
            // Approve : Comment와 다르게 리뷰어가 승인을 하는 것으로, 머지해도 괜찮다는 의견을 보내는 것이다. <- 두 번 보내짐.
            // Request changes : 말 그대로 변경을 요청하는 것으로, 승인을 거부하는 것을 뜻한다.
        cloudEventObj.action_type = 'review';
        cloudEventObj.action_time = JSON.stringify(obj.review.submitted_at).replace(/['"]+/g, '');
        cloudEventObj.pr_url = JSON.stringify(obj.review.pull_request_url).replace(/['"]+/g, '');
        cloudEventObj.pr_id = JSON.stringify(obj.pull_request.id).replace(/['"]+/g, '');
        cloudEventObj.commit_id = JSON.stringify(obj.review.commit_id).replace(/['"]+/g, '');
        cloudEventObj.state = JSON.stringify(obj.review.state).replace(/['"]+/g, '');

    }else if(cloudEventObj.action == 'opened'){
        // PR Opened 발생 시
        // commits들에 대한 정보는 Parser에서 받아서 처리하는 것으로 진행할 예정.
        // action_type, action_id(pr의 id / 이름도 추가 가능하다.), action_time, commits_url, commits_cnt 를 가져올 것이다. 
        context.log("opened!")
        cloudEventObj.action_type = 'pull_request';
        cloudEventObj.action_id = JSON.stringify(obj.pull_request.id).replace(/['"]+/g, '');
        cloudEventObj.action_time = JSON.stringify(obj.pull_request.created_at).replace(/['"]+/g, '');
        cloudEventObj.commits_url = JSON.stringify(obj.pull_request.commits_url).replace(/['"]+/g, '');
        cloudEventObj.commits_cnt = JSON.stringify(obj.pull_request.commits).replace(/['"]+/g, '');
        cloudEventObj.commits_label = JSON.stringify(obj.pull_request.head.label).replace(/['"]+/g, '');
        cloudEventObj.commits_branch = JSON.stringify(obj.pull_request.head.ref).replace(/['"]+/g, '');
    }else if(cloudEventObj.action == 'closed'){
        // PR Closed(Merged, Reject 후 폐기)
        context.log("closed!");
        const merged = JSON.stringify(obj.pull_request.merged_at);
        cloudEventObj.action_time = JSON.stringify(obj.pull_request.closed_at).replace(/['"]+/g, '');
        cloudEventObj.commits_label = JSON.stringify(obj.pull_request.head.label).replace(/['"]+/g, '');
        cloudEventObj.commits_branch = JSON.stringify(obj.pull_request.head.ref).replace(/['"]+/g, '');
        cloudEventObj.merge_to_label = JSON.stringify(obj.pull_request.base.label).replace(/['"]+/g, '');
        cloudEventObj.merge_to_branch = JSON.stringify(obj.pull_request.base.ref).replace(/['"]+/g, '');

        if(merged == 'null'){
            context.log("Closed, but not merged.");
            cloudEventObj.action_type = 'cancel_pull_request';
        }else{
            context.log("Closed, with merged.");
            cloudEventObj.merge_time = JSON.stringify(obj.pull_request.merged_at).replace(/['"]+/g, '');
            cloudEventObj.action_type = 'accept_pull_request';
        }
    }else if(cloudEventObj.action == 'pushed'){
        //pushed 될 경우 어떻게 될 것인가.
        context.log("pushed");
        cloudEventObj.action_type = 'push';
        cloudEventObj.action_time = JSON.stringify(obj.repository.updated_at).replace(/['"]+/g, '');
    }else if(cloudEventObj.action == 'synchronized'){
        context.log("synchronized");
    }else{
        // push의 경우에 가능함을 확인함.
        context.log(cloudEventObj.action);
        context.log("잘 수 없어.");
    }

    // context.log(JSON.stringify(cloudEventObj));

    // EventHubs로 보내는 부분 작업중
    try{
        await send_post.sender(cloudEventObj, context);
        context.res = {
            // status: 200, /* Defaults to 200 */
            body: cloudEventObj
        };
        context.log("test 성공")
    }catch(e){
        context.log(e);
        context.res ={
            body : "실패"
        }
    }

}




