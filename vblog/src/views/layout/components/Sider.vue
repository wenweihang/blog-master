<template>
  <a-layout-sider id="sider" v-model="isCollapsed" collapsible>
    <div class="sider-title" :class="{ 'sider-title-collapsed': isCollapsed }">
      <img
        class="logo-image"
        :class="{ 'logo-image-collapsed': isCollapsed }"
        src="../../../assets/logo.png"
      />
      <span class="logo-title" :class="{ 'logo-title-hidden': isCollapsed }"
        >Blog</span
      >
    </div>
    <a-menu
      :default-selected-keys="['home']"
      mode="inline"
      :selected-keys="[current]"
      @click="handleClick"
    >
      <a-menu-item key="home">
        <a-icon type="home" theme="filled" />
        <span>首页</span>
      </a-menu-item>
      <a-menu-item key="article">
        <a-icon type="block" />
        <span>文章管理</span>
      </a-menu-item>
      <a-menu-item key="category">
        <a-icon type="tag" />
        <span>分类标签</span>
      </a-menu-item>
      <a-menu-item key="comment">
        <a-icon type="form" />
        <span>评论管理</span>
      </a-menu-item>
      <a-sub-menu key="security">
        <span slot="title"
          ><a-icon type="security-scan" /><span>权限</span></span
        >
        <a-menu-item key="user">
          <a-icon type="user" />
          <span>用户管理</span>
        </a-menu-item>
        <a-menu-item key="role">
          <a-icon type="robot" />
          <span>角色管理</span>
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="log">
        <a-icon type="database" />
        <span>日志管理</span>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
export default {
  props: ['collapsed'],
  created() {
    if (this.$router.currentRoute.fullPath !== '/home') {
      this.$router.push('/home')
    }
  },
  data() {
    return {
      isCollapsed: this.collapsed,
      current: 'home'
    }
  },
  methods: {
    handleClick(e) {
      this.current = e.key
      this.$router.push(e.key)
    }
  }
}
</script>

<style scoped lang="scss">
.ant-layout-sider {
  background: #fff;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.12);
}

.sider-title {
  margin: 16px;
}

.logo-image {
  display: inline-block;
  width: 42px;
  margin-right: 16px;
  vertical-align: middle;
  transition: all 0.15s cubic-bezier(0.215, 0.61, 0.355, 1),
    margin 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
}

.logo-title {
  display: inline-block;
  color: rgba(0, 0, 0, 0.85);
  font-size: 28px;
  font-weight: 600;
  opacity: 1;
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
}
.logo-title-hidden {
  position: absolute;
  top: -200px;
  display: inline-block;
  max-width: 0;
  max-height: 0;
  opacity: 0;
}
.logo-image-collapsed {
  margin: 0;
}
.sider-title-collapsed {
  height: 42px;
}
</style>
