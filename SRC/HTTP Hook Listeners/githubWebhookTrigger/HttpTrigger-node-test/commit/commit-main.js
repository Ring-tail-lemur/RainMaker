const getter = require('../getter/get-commits.js');

module.exports ={
    async commitMain(context, commits_url, private){
        if(private == 'true'){
            //사용자 개인 token 받아오는 logic 필요
            const token = '자신의 토큰을 넣도록 하자. 우리는 척척척 스스로 어린이.';
            const commits = await getter.getCommitsWithToken(commits_url, token);
            const pasedCommits = JSON.parse(commits);
        }else{
            const commits = await getter.getCommitsAnyToken(commits_url);
            const pasedCommits = JSON.parse(commits);
        }
    }
}