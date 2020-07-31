<template>
  <div>
    <Header />
    <Content />
  </div>
</template>

<script>
import Header from './components/Header'
import Content from './components/Content'

export default {
  name: 'Article',
  components: {
    Header,
    Content
  }
}
</script>
