<template>
  <a-layout-header id="header">
    <h1 class="blog-title" @click="onLogo">Nico博客</h1>
    <div class="search">
      <a-input-search
        placeholder="搜索博文"
        enter-button="搜索"
        style="width: 300px"
        @search="onSearch"
      />
    </div>
  </a-layout-header>
</template>

<script>
export default {
  methods: {
    onSearch(value) {
      this.$store.commit('edit', { status: 'search', queryParam: value })
    },
    onLogo() {
      if (this.$router.currentRoute.fullPath != '/article') {
        this.$router.replace('/article')
      }
      this.$store.commit('edit', { status: 'search', queryParam: '' })
    }
  }
}
</script>

<style scoped lang="scss">
#header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}
.blog-title {
  cursor: pointer;
}
.search {
  display: flex;
  align-items: center;
}
</style>
