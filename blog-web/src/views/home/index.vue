<template>
  <a-layout>
      <Header />
      <a-layout>
        <Sider />
        <Content />
      </a-layout>
    </a-layout>
</template>

<script>
import Header from './components/Header'
import Content from './components/Content'
import Sider from './components/Sider'

export default {
  name: 'Home',
  components: {
    Header,
    Content,
    Sider
  }
}
</script>

<style scoped lang="scss">
.ant-layout {
  width: 100%;
  min-height: 90vh;
}
.ant-layout-has-sider {
  width: 86%;
  margin: 0 auto;
  padding-bottom: 42px;;
}
</style>
