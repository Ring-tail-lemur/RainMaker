import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
// import HomeView from '../test/components/DoraMetric.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta : {
        header : true
      }
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/buttons',
      name: 'buttons',
      component: () => import('../views/Buttons.vue')
    },
    {
      path: '/cards',
      name: 'cards',
      component: () => import('../views/Cards.vue')
    },
    {
      path: '/tables',
      name: 'tables',
      component: () => import('../views/Tables.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/login.vue'),
      meta : {
        header : false
      }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/Register.vue'),
      meta : {
        header : false
      }
    }
  ]
})

export default router
