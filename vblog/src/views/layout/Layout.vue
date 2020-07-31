<template>
  <a-layout id="components-layout-demo-side" style="min-height: 100vh">
    <Sider :collapsed="collapsed" />
    <a-layout>
      <Header />
      <Content />
      <Footer />
    </a-layout>
  </a-layout>
</template>
<script>
import Sider from '@/views/layout/components/Sider'
import Header from '@/views/layout/components/Header'
import Content from '@/views/layout/components/Content'
import Footer from '@/views/layout/components/Footer'

export default {
  components: {
    Sider,
    Header,
    Content,
    Footer
  },
  data() {
    return {
      collapsed: false
    }
  }
}
</script>

<style>
#components-layout-demo-side .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
