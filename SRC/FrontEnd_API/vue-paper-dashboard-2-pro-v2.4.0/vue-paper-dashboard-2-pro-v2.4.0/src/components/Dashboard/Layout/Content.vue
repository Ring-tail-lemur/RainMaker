<template>
  <div class="content">

    <transition name="fade" mode="out-in">
      <!-- your content here -->
      <router-view @waiting="waiting" @notLogin="notLogin" v-if="!wait"></router-view>
      <loading-main-panel v-if="wait"></loading-main-panel>
    </transition>
  </div>
</template>
<script>
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";
import Button from "@/components/UIComponents/Button";
  export default {
    components : {
      LoadingMainPanel,
      pbutton : Button,
    },
    data() {
      return {
        wait : false
      }
    },
    methods: {
      waiting() {
        alert('Github에서 데이터를 불러오고 있습니다. 잠시만 기다려주세요.');
        this.wait = true;
      },
      notLogin() {
        alert('로그인 후 이용해주세요');
        window.location.replace('/login');
      }
    }
  }
</script>
<style>
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity .15s
  }

  .fade-enter,
  .fade-leave-to
    /* .fade-leave-active in <2.1.8 */

  {
    opacity: 0
  }
</style>
