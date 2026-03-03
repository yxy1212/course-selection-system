import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Courses from '@/views/Courses.vue'
import MyCourses from '@/views/MyCourses.vue'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/courses', component: Courses, meta: { requiresAuth: true } },
  { path: '/my-courses', component: MyCourses, meta: { requiresAuth: true } }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
