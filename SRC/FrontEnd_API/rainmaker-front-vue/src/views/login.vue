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
          <div style="margin-bottom: 10px; " id="titleFont">
            RainMaker
          </div>
          <hr id="line">
          <div style="margin-bottom: 50px; padding: 0 30px" id="contentFont">
            팀의 생산성 향상을 원하시고,
            추적하시고 싶으시다면 로그인하세요. Git을 기반으로 이루어지기 때문에 필수적으로 Git의 접근권한이 필요합니다.
          </div>
          <div style="padding: 0 30px" id="contentFont">
            오른쪽에 서비스로 편리하게 로그인, 회원가입하세요
          </div>
        </div>
        <div class="loginBox">
          <div class="logo">
            <Logo/>
          </div>
          <div class="loginTitle" id="titleFont">
            로그인
          </div>
          <div class="loginInnerBox">
            <div class="githubIconBox">
              <button v-on:click="aaa()"/>
              <button>
                <i class="fa fa-github" style="font-size:48px"></i>
              </button>
            </div>
          </div>
          <div class="githubLoginText" id="contentFont">
            GitHub
          </div>
          <div class="githubRegister">
            <button class="githubRegisterButton"  id="contentFont">
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
import Logo from "../components/Logo.vue"
import axios from "axios";
export default {

  components: {
    Button,
    Logo,
  },
  methods : {
    aaa : async () => {
      window.location.replace('https://github.com/login/oauth/authorize?client_id=8d0d3ac6cafd4bac3358');
    }
  }
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
  margin-bottom: 20px;
  font-size: 24px;
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
  font-size: 25px;
}

#titleFont {
  font-family: 'Jua', sans-serif;
  font-size: 25px;
}

</style>
