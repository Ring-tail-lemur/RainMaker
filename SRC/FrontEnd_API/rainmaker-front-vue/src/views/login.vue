<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Dongle:wght@400;700&family=Jua&display=swap" rel="stylesheet">

  <div class="container grid px-6 mx-auto">

    <div class="login">
      <div>
        <div class="githubExplain">
          <div style="margin-bottom: 10px;">
            RainMaker
          </div>
          <hr id="line">
          <div style="margin-bottom: 50px; padding: 0 30px">
            팀의 생산성 향상을 원하시고,
            추적하시고 싶으시다면 로그인하세요. Git을 기반으로 이루어지기 때문에 필수적으로 Git의 접근권한이 필요합니다.
          </div>
          <div style="padding: 0 30px">
            오른쪽에 서비스로 편리하게 로그인, 회원가입하세요
          </div>
        </div>
        <div class="loginBox">
          <div class="logo">
            로고
          </div>
          <div class="loginTitle">
            로그인
          </div>
          <div class="loginInnerBox">
            <div class="githubIconBox">
              <button />
              <button v-on:click="aaa()">
                <i class="fa fa-github" style="font-size:48px"></i>
              </button>
            </div>
          </div>
          <div class="githubLoginText">
            GitHub
          </div>
          <div class="githubRegister">
            <button class="githubRegisterButton">
              회원가입
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Button from "./Buttons.vue";
export default {
  methods : {
    aaa : async function() {
      const url = this.defaultURL;
      console.log(`URL : , ${url}`, this.clientId);

      let replaceUrl = `https://github.com/login/oauth/authorize?response_type=code&client_id=${this.clientId}&scope=repo%20repo:status%20repo_deployment%20public_repo%20repo:invite%20admin:repo_hook%20write:repo_hook%20read:repo_hook%20admin:org%20admin:public_key&state=8U6-X7-6MVIRmkTQbb-ySo36wSRugfaBNjpHlTVJ0hY%3D&redirect_uri=${url}/login/oauth2/code/github`;
      window.location.replace(replaceUrl);
    }
  },
  components: {Button}
}
</script>

<style>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.login {
  width: 450px;
}
.login {
  min-height: 0;
}
.login {
  -webkit-border-radius: 8px;
  border-radius: 12px;
  border: 1px solid #dadce0;
  display: block;
  -webkit-flex-shrink: 0;
  flex-shrink: 0;
  margin: 0 auto;
  min-height: 0;
  -webkit-transition: .2s;
  transition: .2s;
  width: 700px;
}
.login {
  background: #fff;
  display: -webkit-box;
  display: -webkit-flex;
  display: flex;
  -webkit-flex-direction: column;
  flex-direction: column;
  max-width: 100%;
  position: relative;
  z-index: 2;
}
.login {
  background: #fff;
  direction: ltr;
  font-size: 15px;
  line-height: 1.4286;
  margin: 0;
  padding: 0;
}
.loginBox {
  height: auto;
  min-height: 500px;
}
.loginBox {
  box-sizing: inherit;
  float: right;
  width: 50%;
  padding: 48px 40px 36px;
}
.loginInnerBox{
  display: flex;
  justify-content: center;
  align-items: center;
}
.githubIconBox{
  border: solid 3px #c0bfbf;
  border-radius: 10px;
  padding: 5px 8px;
}
.githubExplain {
  -webkit-border-bottom-left-radius: 8px;
  -webkit-border-top-left-radius: 8px;
  background: #c4ddff;
  float: left;
  width: 50%;
  height: auto;
  box-sizing: inherit;
  min-height: 500px;
  padding: 48px 40px 36px;
}
.githubLoginText{
  margin-bottom: 50px;
  font-weight:bold;
}
.loginTitle {
  margin-bottom: 40px;
  font-size: 24px;
  font-weight:bold;
}
.logo{
  margin-bottom: 50px;
}
#line {
  width: 50%;
  margin-left: auto;
  margin-right: auto;
  border: 0;
  border-top: 3px solid;
  margin-bottom: 20px;
}
.githubRegisterButton{
  border: solid 3px #c0bfbf;
  border-radius: 10px;
  padding: 5px 8px;
  width: 100px;
}
#contentFont {
  font-family: 'Dongle', sans-serif;
  font-family: 'Jua', sans-serif;
}
#titleFont {
  font-family: 'Jua', sans-serif;
}
</style>