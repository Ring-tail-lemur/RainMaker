<template>
  <div>
    <loading-main-panel></loading-main-panel>
  </div>
</template>
<script>
import axios from "axios";
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";

export default {
  components :{
    LoadingMainPanel
  },
  created() {
    this.setCookie();
  },
  methods : {
    async setCookie() {
      let code, state;
      code = this.$route.query.code;
      state = this.$route.query.state;

      if(!code || !state) {
        console.error("query is not init");
        return;
      }

      console.log(code, state);
      try {
        const response = await axios({
          method: "get",
          url: this.custom.defaultURL + `/api/user/login/oauth2/code/github?code=${code}&state=${state}`
        });
        const jwtToken = response.data;

        console.log("SESSIONID", jwtToken);

        let date = new Date();
        date.setTime(date.getTime() + (1 * 24 * 60 * 60 * 1000));
        const expires = " expires=" + date.toGMTString() + "; ";
        document.cookie = "SESSIONID=" + jwtToken + ";path=/; " + expires;
        window.location.replace(this.custom.myURL + "/dashboard");
      } catch (e) {
        console.error("로그인 에러. 다시시도", e);
      }
    }
  }
}
</script>
