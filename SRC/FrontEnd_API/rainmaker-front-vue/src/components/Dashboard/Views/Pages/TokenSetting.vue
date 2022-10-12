<template>
  <div class="register-page">
    <app-navbar></app-navbar>
    <div class="wrapper wrapper-full-page ">
      <div class="full-page register-page section-image" filter-color="black">
        <div class="content">
          <div class="container">
            <div class="row">
              <div class="col-lg-4 col-md-6 mr-auto ml-auto">
                <card type="signup" class="text-center h-full">
                  <template slot="header">
                    <h4 class="card-title mt-4">Github Token</h4>
                  </template>
                  <div class="text-left ">
                    1. <a href="https://github.com/settings/tokens/new" target="_blank">GitHub Token 발급 페이지</a>에
                    들어가세요.<br>
                    2. 아래의 스코프를 <b>꼭</b> 체크해주세요!<br>
                    <div class="text-center m-3">
                      <span class="github-webhook-scope">read:discussion</span>
                      <span class="github-webhook-scope">read:org</span>
                      <span class="github-webhook-scope">read:user</span>
                      <span class="github-webhook-scope">repo</span>
                      <span class="github-webhook-scope">user:email</span>
                      <span class="github-webhook-scope">read:repo_hook</span>
                      <span class="github-webhook-scope">write:repo_hook</span>
                    </div>
                  </div>
                  <p slot="footer" type="info">
                    <b><span style="" v-bind:style="{color:getBorderColor()}">{{ checkString }}</span></b>
                    <input v-model="githubToken"
                           v-bind:style="{borderColor:getBorderColor()}"
                           class="w-11/12 mt-3"
                           @blur="verifyToken()"
                           style="width: 100%; margin-bottom: 20px; height: 40px" type="text"
                           placeholder="github token을 입력해주세요!"></input>
                    <p-button round block class="mb-3" v-on:click="saveToken" :disabled="!isChecking">확인</p-button>
                  </p>
                </card>
              </div>
            </div>
          </div>
        </div>
        <div class="full-page-background"
             style="background-image: url(/static/img/background/background-devops-2.jpg)"></div>
      </div>
    </div>
  </div>
</template>

<script>
import {Button, Card, Checkbox, InfoSection} from "@/components/UIComponents";
import AppNavbar from "@/components/Dashboard/Views/Pages/Layout/AppNavbar";
import AppFooter from "@/components/Dashboard/Views/Pages/Layout/AppFooter";
import axios from "axios";
import setHeaderJWT from "@/api/setHeaderJWT";
import pageCheckAndChange from "@/util/pageCheckAndChange";

export default {
  name: "TokenSetting",
  components: {
    Card,
    AppNavbar,
    AppFooter,
    InfoSection,
    [Checkbox.name]: Checkbox,
    [Button.name]: Button
  },
  data() {
    return {
      githubToken: '',
      tokenValidation: 'start',
      checkString: '',
      isChecking: false
    }
  },
  methods: {
    async saveToken() {
      await axios({
        headers: setHeaderJWT(),
        method: "post",
        url: this.custom.defaultURL + "/token",
        data: {
          token: this.githubToken
        }
      });
      window.location.replace(this.custom.myURL + "/RepositorySelect");
    },
    verifyToken() {
      axios({
        url: "https://api.github.com/user",
        method: "get",
        headers: {
          Authorization: `Bearer ${this.githubToken}`
        }
      }).then(response => {
        if (response.status === 200) {
          this.tokenValidation = true;
          this.checkString = '토큰이 확인되었습니다!';
          this.isChecking = true;
        } else {
          this.tokenValidation = false;
          this.checkString = '토큰을 다시 확인해주세요!';
          this.isChecking = false;
        }
      }).catch(error => {
        this.tokenValidation = false;
        this.checkString = '토큰을 다시 확인해주세요!';
        this.isChecking = false;
      });
    },
    getBorderColor() {
      if (this.tokenValidation === 'start') {
        return 'basic';
      }
      if (this.tokenValidation === true) {
        return 'green';
      }
      return 'red';
    },

  },
  beforeDestroy() {
    this.closeMenu()
  }
}
</script>

<style scoped>

</style>
