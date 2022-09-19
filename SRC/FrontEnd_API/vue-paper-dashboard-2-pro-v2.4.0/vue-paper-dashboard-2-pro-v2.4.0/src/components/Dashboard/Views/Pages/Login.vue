<template>
  <div class="login-page">
    <app-navbar></app-navbar>
    <div class="wrapper wrapper-full-page">
      <div class="full-page login-page section-image">
        <!--   you can change the color of the filter page using: data-color="blue | azure | green | orange | red | purple" -->
        <div class="content">
          <div class="container">
            <div class="col-lg-4 col-md-6 ml-auto mr-auto">
              <form @submit.prevent="login">
                <card type="login">
                  <h3 slot="header" class="header text-center">로그인</h3>

                  <p>팀의 생산성 향상을 원하시고,
                    추적하시고 싶으시다면 로그인하세요. Git을 기반으로 이루어지기 때문에 필수적으로 Git의 접근권한이 필요합니다.</p>
                  <p>아래의 서비스로 편리하게 로그인, 회원가입하세요</p>

                  <br>


                  <div class="text-center">
                    <div class="loginInnerBox">
                      <div v-on:click="githubChange()" class="githubIconBox">
                          <i class="fa fa-github" style="font-size:48px"></i>
                      </div>
                    </div>
                    <div class="githubLoginText">
                      GitHub
                    </div>
                  </div>

                  <br>

                  <p-button native-type="submit" slot="footer" round block class="mb-3">회원가입</p-button>
                </card>
              </form>
            </div>
          </div>
        </div>
        <div class="full-page-background" style="background-image: url(static/img/background/background-2.jpg) "></div>
      </div>
    </div>
  </div>
</template>
<script>
  import { Card, Checkbox, Button } from 'src/components/UIComponents';
  import AppNavbar from './Layout/AppNavbar'
  import AppFooter from './Layout/AppFooter'

  export default {
    components: {
      Card,
      AppNavbar,
      AppFooter,
      [Checkbox.name]: Checkbox,
      [Button.name]: Button
    },
    methods: {
      toggleNavbar() {
        document.body.classList.toggle('nav-open')
      },
      closeMenu() {
        document.body.classList.remove('nav-open')
        document.body.classList.remove('off-canvas-sidebar')
      },
      login() {
        // handle login here
      },
      async githubChange() {
        const url = this.custom.myURL;
        console.log(`URL : , ${url}`, this.custom.clientId);

        let replaceUrl = `https://github.com/login/oauth/authorize?response_type=code&client_id=${this.custom.clientId}&scope=repo%20repo:status%20repo_deployment%20public_repo%20repo:invite%20admin:repo_hook%20write:repo_hook%20read:repo_hook%20admin:org%20admin:public_key&state=8U6-X7-6MVIRmkTQbb-ySo36wSRugfaBNjpHlTVJ0hY%3D&redirect_uri=${url}/login/oauth2/code/github`;
        window.location.replace(replaceUrl);
      }
    },
    data() {
      return {
        form: {
          username: '',
          password: ''
        }
      }
    },
    beforeDestroy() {
      this.closeMenu()
    }
  }
</script>
<style>
</style>
