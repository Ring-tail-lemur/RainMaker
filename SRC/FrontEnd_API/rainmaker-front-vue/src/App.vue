<template>
  <div class="flex h-screen bg-gray-50 dark:bg-gray-900" id="app">
    <Sidebar :isSideMenuOpen="isSideMenuOpen" @close-side-menu="isSideMenuOpen = false" v-show="$route.meta.header"/>

    <div class="flex flex-col flex-1 w-full">
      <Navigation @open-side-menu="isSideMenuOpen = !isSideMenuOpen" v-show="$route.meta.header"/>
      <div class="p-6 dark:text-white">
        <RouterView />
      </div>
    </div>
  </div>
</template>

<script> // setup lang="ts">
import { ref } from "vue";
import "@/assets/main.min.css"

import { RouterView } from 'vue-router'
import Sidebar from './components/Sidebar.vue'
import Navigation from './components/Navigation.vue'


export default {
  components : {
    Sidebar,
    Navigation,
    RouterView,
  },
  data() {
    return {
      value : true,
    }
  }
}

const isSideMenuOpen = ref(false);

// console.log(this.$route.params);
</script>