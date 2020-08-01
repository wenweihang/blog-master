import Vue from 'vue'
import VueRouter from 'vue-router'
import { getToken } from '@/utils/auth'
import Layout from '@/views/layout/Layout'

Vue.use(VueRouter)

const routes = [{
  path: '/login',
  component: () => import('@/views/login/index')
}, {
  path: '/write',
  name: 'Write',
  component: () => import('@/views/write/index')
}, {
  path: '/',
  component: Layout,
  redirect: 'home',
  children: [{
    path: 'home',
    name: 'Home',
    component: () => import('@/views/home/index')
  }, {
    path: 'article',
    name: 'Article',
    component: () => import('@/views/article/index')
  }, {
    path: 'category',
    name: 'Category',
    component: () => import('@/views/category/index')
  }, {
    path: 'comment',
    name: 'Comment',
    component: () => import('@/views/comment/index')
  }, {
    path: 'user',
    name: 'User',
    component: () => import('@/views/security/user/index')
  }, {
    path: 'role',
    name: 'Role',
    component: () => import('@/views/security/role/index')
  }, {
    path: 'setting',
    name: 'Setting',
    component: () => import('@/views/setting/index')
  }, {
    path: 'log',
    name: 'Log',
    component: () => import('@/views/log/index')
  }, {
    path: '404',
    name: '404',
    component: () => import('@/views/errors/404')
  }, {
    path: '403',
    name: '403',
    component: () => import('@/views/errors/403')
  }]
}, {
  path: '*',
  redirect: '404'
}]

const router = new VueRouter({
  routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  // 获取token
  const token = getToken()
  if (!token) return next('/login')
  next()
})

export default router
