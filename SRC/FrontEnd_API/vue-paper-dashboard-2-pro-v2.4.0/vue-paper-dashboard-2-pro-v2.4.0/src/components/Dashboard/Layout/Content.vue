<template>
  <div class="content">

    <transition name="fade" mode="out-in">
      <!-- your content here -->
      <router-view></router-view>
    </transition>
  </div>
</template>
<script>
import LoadingMainPanel from "@/components/Dashboard/Layout/LoadingMainPanel";
import Button from "@/components/UIComponents/Button";
  export default {
    components : {
      LoadingMainPanel,
      pbutton : Button
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
