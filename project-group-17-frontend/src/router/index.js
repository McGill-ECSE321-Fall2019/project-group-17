import Vue from 'vue'
import Router from 'vue-router'

import Hello from '@/components/Hello.vue'
import Login from '@/components/Login.vue'
import Signup from '@/components/Signup.vue'
import CreateCourse from '@/components/CreateCourse.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup
    },
    {
      path: '/createCourse',
      name: 'CreateCourse',
      component: CreateCourse
    }

  ]
})
