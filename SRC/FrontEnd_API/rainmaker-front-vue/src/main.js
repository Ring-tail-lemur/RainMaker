/*!

  =========================================================
  * Vue Paper Dashboard 2 PRO - V2.4.0
  =========================================================

  * Product Page: https://www.creative-tim.com/product/vue-paper-dashboard-2-pro
  * Available with purchase of license from https://www.creative-tim.com/product/vue-paper-dashboard-2-pro
  * Copyright 2019 Creative Tim (https://www.creative-tim.com)
  * License Creative Tim (https://www.creative-tim.com/license)

  =========================================================

*/

import Vue from 'vue'
import './pollyfills'
import VueRouter from 'vue-router'
import VueNotify from 'vue-notifyjs'
import lang from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'
import App from './App.vue'

// Plugins
import GlobalComponents from './globalComponents'
import GlobalDirectives from './globalDirectives'
import SideBar from './components/UIComponents/SidebarPlugin'
import initProgress from './progressbar';

// router setup
import routes from './routes/routes'

// library imports

import './assets/sass/paper-dashboard.scss'
import './assets/sass/demo.scss'

import sidebarLinks from './sidebarLinks'
import './registerServiceWorker'
// plugin setup

Vue.prototype.custom = {
  myURL : "http://127.0.0.1:3000",
  // myURL : "https://victorious-forest-095d4a310.1.azurestaticapps.net",
  defaultURL : "http://127.0.0.1:8080",
  // defaultURL : "https://spring-api-server.azurewebsites.net",
  clientId : "8189c16057d124b9324e",
  // clientId : "42286a47489496b3129b",


  endURL : "/login/oauth2/code/github"
}

Vue.use(VueRouter)
Vue.use(GlobalDirectives)
Vue.use(GlobalComponents)
Vue.use(VueNotify)
Vue.use(SideBar, {sidebarLinks: sidebarLinks})
locale.use(lang)

// configure router
const router = new VueRouter({
  routes, // short for routes: routes
  linkActiveClass: 'active',
  mode: 'history',
  scrollBehavior: (to) => {
    if (to.hash) {
      return {selector: to.hash}
    } else {
      return { x: 0, y: 0 }
    }
  }
})

initProgress(router);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router
})
