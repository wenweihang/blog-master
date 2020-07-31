import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
  path: '/',
  name: 'Home',
  redirect: '/article',
  component: () => import('@/views/home/index'),
  children: [
    {
      path: '/article',
      name: 'Article',
      component: () => import('@/views/article/index')
    },
    {
      path: '/article/:id',
      name: 'ArticleDetail',
      component: () => import('@/views/articleDetail/index')
    }
  ]
}]

const router = new VueRouter({
  routes
})

export default router
