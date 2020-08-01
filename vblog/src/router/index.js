import Vue from 'vue'
import VueRouter from 'vue-router'
import { getToken } from '@/utils/auth'
import Layout from '@/views/layout/Layout'

Vue.use(VueRouter)

const routes = [{
  path: '/login',
  component: () => import(/* webpackChunkName: 'group-login' */ '@/views/login/index')
}, {
  path: '/write',
  name: 'Write',
  component: () => import(/* webpackChunkName: 'group-write' */ '@/views/write/index')
}, {
  path: '/',
  component: Layout,
  redirect: 'home',
  children: [{
    path: 'home',
    name: 'Home',
    component: () => import(/* webpackChunkName: 'group-home' */ '@/views/home/index')
  }, {
    path: 'article',
    name: 'Article',
    component: () => import(/* webpackChunkName: 'group-article' */ '@/views/article/index')
  }, {
    path: 'category',
    name: 'Category',
    component: () => import(/* webpackChunkName: 'group-category' */ '@/views/category/index')
  }, {
    path: 'comment',
    name: 'Comment',
    component: () => import(/* webpackChunkName: 'group-comment' */ '@/views/comment/index')
  }, {
    path: 'user',
    name: 'User',
    component: () => import(/* webpackChunkName: 'group-security' */ '@/views/security/user/index')
  }, {
    path: 'role',
    name: 'Role',
    component: () => import(/* webpackChunkName: 'group-security' */ '@/views/security/role/index')
  }, {
    path: 'setting',
    name: 'Setting',
    component: () => import(/* webpackChunkName: 'group-setting' */ '@/views/setting/index')
  }, {
    path: 'log',
    name: 'Log',
    component: () => import(/* webpackChunkName: 'group-log' */ '@/views/log/index')
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
