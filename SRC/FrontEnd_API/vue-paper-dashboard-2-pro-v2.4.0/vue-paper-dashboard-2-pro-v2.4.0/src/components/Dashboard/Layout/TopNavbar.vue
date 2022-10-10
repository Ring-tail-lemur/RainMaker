<template>
  <navbar v-model="showNavbar">
    <div class="navbar-wrapper">
      <div class="navbar-minimize">
        <button id="minimizeSidebar" class="btn btn-icon btn-round" @click="minimizeSidebar">
          <i class="nc-icon nc-minimal-right text-center visible-on-sidebar-mini"></i>
          <i class="nc-icon nc-minimal-left text-center visible-on-sidebar-regular"></i>
        </button>
      </div>
      <div class="navbar-toggle">
        <navbar-toggle-button @click.native="toggleSidebar">

        </navbar-toggle-button>
      </div>
      <a class="navbar-brand" href="#pablo">RainMaker</a>
    </div>

    <template slot="navbar-menu">
      <form>
        <div class="input-group no-border">
          <input type="text" value="" class="form-control" placeholder="Search...">
          <div class="input-group-append">
            <div class="input-group-text">
              <i class="nc-icon nc-zoom-split"></i>
            </div>
          </div>
        </div>
      </form>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link btn-magnify" href="#pablo">
            <i class="nc-icon nc-layout-11"></i>
            <p>
              <span class="d-lg-none d-md-block">Stats</span>
            </p>
          </a>
        </li>

        <li class="nav-item logoutBox" v-if="this.isLogin" v-on:click="logout()">
          <a class="nav-link btn-magnify">
            로그아웃
          </a>
        </li>

        <li class="nav-item" v-else>
          <a class="nav-link btn-magnify" href="/login">
            로그인
          </a>
        </li>

        <drop-down icon="nc-icon nc-bell-55" tag="li"
                   position="right"
                   direction="none"
                   class="nav-item btn-rotate dropdown">
          <a slot="title"
             slot-scope="{isOpen}"
             class="nav-link dropdown-toggle"
             data-toggle="dropdown"
             aria-haspopup="true"
             :aria-expanded="isOpen">
            <i class="nc-icon nc-bell-55"></i>
            <p>
              <span class="d-lg-none d-md-block">Some Actions</span>
            </p>
          </a>
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </drop-down>
        <li class="nav-item">
          <a class="nav-link btn-rotate" href="#pablo">
            <i class="nc-icon nc-settings-gear-65"></i>
            <p>
              <span class="d-lg-none d-md-block">Account</span>
            </p>
          </a>
        </li>
      </ul>
    </template>
  </navbar>
</template>
<script>
  import { Navbar, NavbarToggleButton } from 'src/components/UIComponents'
  import axios from "axios";
  import setHeaderJWT from "@/util/setHeaderJWT";


  export default {
    components: {
      Navbar,
      NavbarToggleButton
    },
    data() {
      return {
        activeNotifications: false,
        showNavbar: false,
        isLogin : false
      }
    },
    created() {
      let cookie = this.getCookie("SESSIONID");
      if(cookie) {
        this.isLogin = true;
      }
    },
    methods: {
      capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1)
      },
      toggleNotificationDropDown() {
        this.activeNotifications = !this.activeNotifications
      },
      closeDropDown() {
        this.activeNotifications = false
      },
      toggleSidebar() {
        this.$sidebar.displaySidebar(!this.$sidebar.showSidebar)
      },
      hideSidebar() {
        this.$sidebar.displaySidebar(false)
      },
      minimizeSidebar() {
        this.$sidebar.toggleMinimize()
      },
      toggleNavbar() {
        this.showNavbar = !this.showNavbar;
      },
      async logout() {
        try {
          await axios({
            headers: setHeaderJWT(),
            method: "get",
            url: this.custom.defaultURL + '/custom-logout'
          });
        } catch (e) {
          console.log(e);
        } finally {
          this.deleteCookie("SESSIONID");
        }
      },
      getCookie(name) {
        var nameOfCookie = name + "=";
        var x = 0;
        while (x <= document.cookie.length) {
          var y = (x + nameOfCookie.length);
          if (document.cookie.substring(x, y) == nameOfCookie) {
            let endOfCookie;
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
              endOfCookie = document.cookie.length;
            return unescape(document.cookie.substring(y, endOfCookie));
          }
          x = document.cookie.indexOf(" ", x) + 1;
          if (x == 0)
            break;
        }
        return null;
      },
      deleteCookie(name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        location.reload();
      }
    }
  }

</script>
<style>

</style>
