<template>
  <navbar navbar-menu-classes="navbar-right" v-model="showMenu">
    <div class="navbar-wrapper">
      <a class="navbar-brand" href="#pablo">RainMaker</a>
    </div>
    <template slot="navbar-menu">
      <router-link to="/login" class="nav-item" tag="li">
        <a class="nav-link">
          <i class="nc-icon nc-tap-01"></i> 로그인
        </a>
      </router-link>
      <router-link to="/register" class="nav-item" tag="li">
        <a class="nav-link">
          <i class="nc-icon nc-layout-11"></i>회원가입
        </a>
      </router-link>
      <router-link to="/admin" class="nav-item" tag="li">
        <a class="nav-link">
          <i class="nc-icon nc-book-bookmark"></i> 블로그
        </a>
      </router-link>
    </template>
  </navbar>
</template>

<script>
  import {Navbar} from 'src/components/UIComponents'
  export default {
    name: 'main-navbar',
    components: {
      Navbar
    },
    data() {
      return {
        showMenu: false
      }
    }
  }
</script>

<style scoped>

</style>
