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
                    <h4 class="card-title mt-4">Slack URL 등록</h4>
                  </template>
                  <div class="text-left">
                    1. <a href="https://api.slack.com/apps" target="_blank">Slack App 페이지</a>에
                    들어가 새로운 APP을 만들어주세요.<br>
                    2. App Name을 RainMaker로 설정해주세요.<br>
                    3. 만들어진 App을 클릭해 들어간 후, 좌측의 Features/Incoming Webhooks를 선택해주세요.<br>
                    4. Add New Webhook to Workspace를 클릭하여 메시지 받기 원하는 채널과 함께 URL을 만들어주세요.<br>
                    5. URL를 Copy하여 아래에 입력해주세요.<br>
                  </div>
                  <p slot="footer" type="info">
                    <b><span style="" v-bind:style="{color:getBorderColor()}">{{ checkString }}</span></b>
                    <input v-model="slackURL"
                           class="w-11/12 mt-3"
                           style="width: 100%; margin-bottom: 20px; height: 40px" type="text"
                           placeholder="Slack URL을 입력해주세요!"></input>
                    <p-button round block class="mb-3" v-on:click="saveSlackURL">확인</p-button>
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
      slackURL : '',
      checkString: '',
      tokenValidation : 'start',
    }
  },
  methods: {
    async saveSlackURL() {

      if(!this.slackURL.includes("https://hooks.slack.com/services")) {
        this.tokenValidation = null;
        this.checkString = 'URL을 다시 확인해주세요!'
        return
      }
      this.tokenValidation = true;
      this.checkString = 'URl이 확인되었습니다.'

      await axios({
        headers: setHeaderJWT(),
        method: "post",
        url: this.custom.defaultURL + "/api/user/slack/url",
        data: {
          slackUrl : this.slackURL
        }
      });
      window.location.replace(this.custom.myURL + "/dashboard");
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
