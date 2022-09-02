<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Dongle:wght@400;700&family=Jua&display=swap" rel="stylesheet">
  <div class="container grid px-6 mx-auto">
    <div class="login">
      <div class="loginBox">
        <div class="loginInnerBox">
          <img src="../assets/rainMakerLogoFit.png" id="topLogo"><br>
          <p id="functionName">회원가입</p>
          <hr id="line">
          <p id="functionExplanation">아래의 서비스로 편리하게<br>
            연동하여 회원가입 하세요</p>
          <button id="githubRegisterButton" v-on:click="registerWithGithubOAuth">
            <i class="fa fa-github" style="font-size:48px"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  methods: {
    registerWithGithubOAuth : function registerWithGithubOAuth() {
      let clientID = "549e791147c93aef5e5c"
      let clientSecret = "f4376df5afc35b2310c8feb9a82cdc641d8b378d"
      // alert("hello")

      // axios.get({
      //   method: 'get',
      //   url: `https://github.com/login/oauth/authorize?client_id=${clientID}`,
      //   headers: {
      //     accept: 'application/json',
      //   },
      //   params: {
      //     // client_id: clientID,
      //     // client_secret: clientSecret
      //   }
      // })
      window.location.replace("https://github.com/login/oauth/authorize?client_id=549e791147c93aef5e5c");
      // axios.get('https://github.com/login/oauth/authorize?client_id=549e791147c93aef5e5c').then((response)=>{
      //   console.log(response.data);
      // }).catch((error)=>{
      //   console.log(error);
      // })
    }
  }
};



</script>

<style>
#githubRegisterButton {
  border: solid 3px #c0bfbf;
  border-radius: 10px;
  padding: 5px 8px;
}

#functionExplanation {
  font-family: 'Dongle', sans-serif;
  font-size: 25px;
  margin: 30px;
}

#functionName {
  margin-bottom: 10px;
  font-family: 'Jua', sans-serif;
  font-size: 25px;
}

#topLogo {
  margin: 0 auto;
  margin-top: 20px;
  margin-bottom: 10px;
  padding: 0;
  height: 70px;
  display: block;
  align-self: center;
}

#line {
  width: 50%;
  margin-left: auto;
  margin-right: auto;
  border: 0;
  border-top: 3px solid;
}

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
  border-radius: 8px;
  border: 1px solid #dadce0;
  display: block;
  -webkit-flex-shrink: 0;
  flex-shrink: 0;
  margin: 0 auto;
  min-height: 0;
  -webkit-transition: .2s;
  transition: .2s;
  width: 450px;
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
}

.loginInnerBox {
  text-align: center;
  /*padding: 48px 40px 36px;*/
}
</style>
