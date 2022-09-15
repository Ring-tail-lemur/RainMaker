<template></template>
<script>
import axios from "axios";

export default {
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
          url: this.defaultURL + `/login/oauth2/code/github?code=${code}&state=${state}`
        });
        const jwtToken = response.data;

        console.log("JWTTOKEN", jwtToken);
        document.cookie = "rainmaker_jwt=" + jwtToken + ";path=/";
        window.location.replace(this.myURL);
      } catch (e) {
        console.error("로그인 에러. 다시시도", e);
      }
    }
  }
}
</script>