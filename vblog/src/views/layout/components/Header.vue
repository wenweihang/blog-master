<template>
  <a-layout-header id="header">
    <a-button type="primary" @click="onWriteBtn">
      <i class="md-write"></i>
      <span>写博客</span>
    </a-button>
    <div class="right">
      <a-dropdown>
        <div class="user">
          <img class="user-avatar" alt="user-avatar" :src="avatar" />
          <span class="username" v-text="username"></span>
        </div>
        <a-menu slot="overlay" @click="handleMenuClick">
          <a-menu-item key="setting"
            ><a-icon type="setting" />个人设置
          </a-menu-item>
          <a-menu-item key="logout"
            ><a-icon type="logout" />退出登录</a-menu-item
          >
        </a-menu>
      </a-dropdown>
    </div>
  </a-layout-header>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters(['avatar', 'username'])
  },
  data() {
    return {
      notificationCount: 1
    }
  },
  methods: {
    handleMenuClick(e) {
      if (e.key == 'logout') {
        this.$store.dispatch('LogOut')
        this.$router.replace('/login')
      } else if (e.key == 'setting') {
        this.$router.replace('/setting')
      }
    },
    onWriteBtn() {
      this.$router.replace('/write')
    }
  }
}
</script>

<style scoped lang="scss">
#header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
}
.ant-layout-header {
  padding: 0 24px;
}
.md-write {
  width: 16px;
  height: 16px;
  margin-top: -3px;
  margin-right: 6px;
  background: url('../../../assets/write.png') center center no-repeat;
  background-size: contain;
  vertical-align: middle;
}
.md-write + span {
  font-size: 14px;
}

.right {
  display: flex;
  align-items: center;
  height: 100%;
}

.user {
  height: 90%;
  padding: 0 15px;
  cursor: pointer;
  color: rgba(0, 0, 0, 0.85);
}

.user:hover,
.ant-badge:hover {
  background: #f0f0f09f;
}

.user-avatar {
  width: 42px;
  height: 42px;
  margin-right: 10px;
  border-radius: 50%;
  vertical-align: middle;
}
.username {
  margin-right: 8px;
  font-size: 18px;
}

.ant-badge {
  padding: 0 10px;
  margin-right: 8px;
  line-height: 64px;
  cursor: pointer;
}
</style>
