<template>
  <div class="login">
    <div class="container">
      <div class="left">
        <div class="left-content">
          <h1>WELCOME</h1>
          <p>欢迎来到Blog管理系统</p>
        </div>
      </div>
      <div class="right">
        <div class="login-form">
          <div class="login-form-title">Blog管理系统登录</div>
          <a-form-model :model="loginForm" ref="loginForm" :rules="loginRules">
            <a-form-model-item prop="username">
              <a-input
                v-model="loginForm.username"
                placeholder="用户名"
                :maxLength="10"
              >
              </a-input>
            </a-form-model-item>
            <a-form-model-item prop="password">
              <a-input
                v-model="loginForm.password"
                placeholder="密码"
                type="password"
                :maxLength="20"
              >
              </a-input>
            </a-form-model-item>
            <a-form-model-item>
              <a-button
                type="primary"
                size="large"
                @click="onSubmit"
                :loading="loading"
                >登录</a-button
              >
            </a-form-model-item>
          </a-form-model>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { setCookie } from '@/utils/support'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 2,
            max: 10,
            message: '用户名长度应该在3到10之间',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 3,
            max: 20,
            message: '密码长度应该在3到20之间',
            trigger: 'blur'
          }
        ]
      },
      loading: false
    }
  },
  methods: {
    onSubmit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store
            .dispatch('Login', this.loginForm)
            .then(() => {
              this.loading = false
              setCookie('username', this.loginForm.username, 1)
              this.$router.push({ path: '/' })
            })
            .catch(() => {
              this.loading = false
            })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.login {
  position: relative;
  width: 100%;
  height: 100%;
  background: #f9f9f9;

  .container {
    display: flex;
    height: 480px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translateX(-50%) translateY(-50%);
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.12);
    background: #fff;
    .left {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 300px;
      padding: 20px;
      background: #1890ff;
    }
    h1 {
      margin-bottom: 0;
      font-size: 36px;
      letter-spacing: 4px;
      color: #fff;
      font-weight: 400;
      text-align: center;
    }
    p {
      color: rgb(233, 233, 233);
      margin-bottom: 20px;
      text-align: center;
    }
    .right {
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 62px;
    }
    .login-form {
      width: 380px;
      padding: 20px;
    }
    .login-form-title {
      margin-bottom: 28px;
      font-weight: 600;
      font-size: 24px;
      color: #1890ff;
      text-align: center;
    }

    .ant-btn {
      width: 100%;
      margin-top: 24px;
    }
  }
}
</style>
