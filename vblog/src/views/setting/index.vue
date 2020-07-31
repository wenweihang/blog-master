<template>
  <div id="setting">
    <h1>个人资料</h1>
    <div class="user-info">
      <div class="header" @click="onHeader">
        <a-upload
          name="file"
          :data="uploadAvatarData"
          :action="ossUploadUrl"
          :multiple="false"
          :beforeUpload="beforeUpload"
          @change="handleUploadImagesChange"
        >
          <img :src="avatar" alt="user-avatar">
          <p class="modify">修改头像</p>
        </a-upload>
      </div>
      <div class="right">
        <div class="right-info">
          <a-tag color="pink">{{ roles }}</a-tag>
          <p class="username">用户名：{{ username }}</p>
          <div class="right-modify-box">
            <p class="modify" @click="onModifyUserInfo">修改资料</p>
            <p class="modify" @click="onModifyPassword">修改密码</p>
          </div>
        </div>
        <!-- 修改用户信息 -->
        <a-form-model v-if="isModify" ref="modifyForm" :model="modifyForm" :rules="modifyRules" v-bind="modifyLayout">
          <a-form-model-item label="昵称" prop="nickname">
            <a-input v-model="modifyForm.nickname" :maxLength="10" />
          </a-form-model-item>
          <a-form-model-item label="邮箱" prop="email">
            <a-input v-model="modifyForm.email" :maxLength="30" />
          </a-form-model-item>
          <a-form-model-item label="简介" prop="intro">
            <a-textarea v-model="modifyForm.intro" :maxLength="200" :auto-size="{ minRows: 6, maxRows: 6}" />
             <span class="description-count"
            >{{ modifyForm.intro != null ? modifyForm.intro.length : 0 }}/200</span
          >
          </a-form-model-item>
          <a-form-model-item :wrapper-col="{ span: 8, offset: 4 }">
            <a-button class="btn" @click="onModifyCancel">取消</a-button>
            <a-button type="primary" class="btn" @click="onModifyOk">确定</a-button>
          </a-form-model-item>
        </a-form-model>
         <!-- 显示用户信息 -->
        <ul v-else class="self">
          <li class="comon">昵称：{{ user.nickname }}</li>
          <li class="comon">邮箱：{{ user.email }}</li>
          <li class="comon intro">
            <span class="label">简介：</span>
            <span class="content">{{ user.intro }}</span>
          </li>
        </ul>
      </div>
    </div>
    <!-- 修改密码 -->
    <a-modal
      v-model="modifyPasswordModalVisible"
      title="修改密码"
      @cancel="handleModifyPasswordCancel"
      @ok="handleModifyPasswordOk"
      cancelText="取消"
      okText="修改"
    >
      <a-form-model
        ref="passwordForm"
        :model="passwordForm"
        :rules="passwordRules"
        v-bind="passwordFormLayout"
      >
        <a-form-model-item label="原密码" prop="rawPassword">
          <a-input-password
            v-model="passwordForm.rawPassword"
            placeholder="原密码"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item label="新密码" prop="newPassword">
          <a-input-password
            v-model="passwordForm.newPassword"
            placeholder="原密码"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item label="再次输入新密码" prop="checkNewPassword">
          <a-input-password
            v-model="passwordForm.checkNewPassword"
            placeholder="再次输入新密码"
            :maxLength="20"
          />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getCookie } from '@/utils/support'
import { getUsers, updateUser, updateUserPassword } from '@/api/user'
import { message } from 'ant-design-vue'
import { getpolicy } from '@/api/oss'

export default {
  name: 'Setting',
  computed: {
    ...mapGetters(['avatar', 'username']),
    roles: function() {
      const roles = JSON.parse(getCookie('roles'))
      return roles[0].slice(5)
    }
  },
  mounted() {
    this.getUserDetail()
  },
  data() {
    // 修改密码: 验证新密码
    const validateNewPassword = (rule, value, callback) => {
      if (value != this.passwordForm.newPassword) {
        return callback(new Error('两次密码不一致'))
      } else {
        return callback()
      }
    }
    return {
      user: {},
      isModify: false,
      modifyLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 8 }
      },
      modifyForm: {},
      modifyRules: {
        nickname: [
          {
            max: 10,
            message: '昵称的长度不能超过10个字符',
            trigger: 'blur'
          }
        ],
        email: [
          {
            pattern: '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
            message: '邮箱格式不正确',
            trigger: 'blur'
          },
          {
            max: 30,
            message: '邮箱的长度不能超过30个字符',
            trigger: 'blur'
          }
        ],
        intro: {
          max: 200,
          message: '简介的长度不能超过200个字符',
          trigger: 'blur'
        }
      },
      modifyPasswordModalVisible: false,
      passwordForm: {
        rawPassword: '',
        newPassword: '',
        checkNewPassword: ''
      },
      passwordRules: {
        rawPassword: [
          {
            required: true,
            min: 2,
            max: 20,
            message: '密码的长度在2到20之间',
            trigger: 'blur'
          }
        ],
        newPassword: [
          {
            required: true,
            min: 2,
            max: 20,
            message: '密码的长度在2到20之间',
            trigger: 'blur'
          }
        ],
        checkNewPassword: [
          { required: true, validator: validateNewPassword, trigger: 'blur' }
        ]
      },
      passwordFormLayout: {
        labelCol: { span: 6 },
        wrapperCol: { span: 12 }
      },
      ossUploadUrl: 'http://zoe-oss.oss-cn-beijing.aliyuncs.com',
      uploadAvatarData: {
        policy: '',
        signature: '',
        key: '',
        ossaccessKeyId: '',
        dir: '',
        host: ''
        // callback:'',
      }
    }
  },
  methods: {
    getUserDetail() {
      getUsers(this.username).then(response => {
        if (response && response.data) {
          const user = response.data.list[0]
          this.user = user
        }
      })
    },
    onModifyUserInfo() {
      this.isModify = true
      this.modifyForm = this.user
    },
    onModifyCancel() {
      this.isModify = false
      this.$refs.modifyForm.resetFields()
    },
    onModifyOk() {
      this.$refs.modifyForm.validate(valid => {
        if (!valid) return false
        updateUser(this.modifyForm).then(response => {
          if (response && response.data) {
            message.success('修改成功')
            this.getUserDetail()
            this.isModify = false
            this.$refs.modifyForm.resetFields()
          }
        })
      })
    },
    onModifyPassword() {
      this.modifyPasswordModalVisible = true
    },
    handleModifyPasswordCancel() {
      this.modifyPasswordModalVisible = false
      this.$refs.passwordForm.resetFields()
    },
    handleModifyPasswordOk() {
      this.$refs.passwordForm.validate(valid => {
        if (valid) {
          updateUserPassword(
            this.user.id,
            this.passwordForm.rawPassword,
            this.passwordForm.newPassword
          ).then(response => {
            if (response && response.data) {
              this.modifyPasswordModalVisible = false
              this.$refs.passwordForm.resetFields()
              message.success('修改密码成功')
            }
          })
        } else {
          return false
        }
      })
    },
    // 上传图片前
    beforeUpload(file, fileList) {},
    handleUploadImagesChange(info) {
      const status = info.file.status
      if (status === 'done') {
        const imageUrl =
          this.uploadAvatarData.host +
          '/' +
          this.uploadAvatarData.dir +
          '/' +
          info.file.name
        this.user.avatar = imageUrl
        this.$store.commit('SET_AVATAR', imageUrl)
        updateUser(this.user).then(response => {
          if (response && response.data) {
            message.success('头像上传成功')
            this.getUserDetail()
          }
        })
      } else if (status === 'error') {
        message.error('头像上传失败')
      }
    },
    // 获取上传图片的签名
    getPolicyData() {
      getpolicy().then(response => {
        if (response && response.data) {
          const data = response.data
          this.uploadAvatarData.policy = data.policy
          this.uploadAvatarData.signature = data.signature
          this.uploadAvatarData.ossaccessKeyId = data.accessKeyId
          this.uploadAvatarData.key = data.dir + '/${filename}'
          this.uploadAvatarData.dir = data.dir
          this.uploadAvatarData.host = data.host
          // this.uploadAvatarData.callback = data.callback
        }
      })
    },
    onHeader() {
      this.getPolicyData()
    }
  }
}
</script>

<style lang="scss" scoped>
#setting {
  min-height: 420px;
  min-width: 1300px;
  padding: 16px 24px;
  background-color: #fff;
  h1 {
    padding-bottom: 16px;
    margin-bottom: 16px;
    font-size: 24px;
    border-bottom: 1px solid #e0e0e0;
  }
}
.user-info::after {
  content: ' ';
  display: block;
  height: 0;
  clear: both;
}
.header {
  float: left;
  margin-right: 16px;
  text-align: center;
  cursor: pointer;
  img {
    display: block;
    width: 100px;
    height: 100px;
    border-radius: 50%;
  }
  p {
    margin-top: 10px;
  }
}
.modify {
  color: #1890ff !important;
  cursor: pointer;
}
.right {
  float: left;
  width: 820px;
}
.right-info {
  padding-top: 28px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e0e0e0;
  .username {
    height: 28px;
    margin-top: 4px;
    font-size: 14px;
    line-height: 28px;
    color: #999;
    font-weight: bold;
  }
}
.right-modify-box {
  display: flex;
  .modify {
    margin-right: 8px;
  }
}
.self {
  padding: 16px 0;
  .comon {
    height: 36px;
    line-height: 36px;
    font-size: 14px;
    color: rgba(0, 0, 0, .85);
  }
}
.self .comon:last-child {
  height: auto;
}
.intro {
  display: flex;
  .label {
    display: block;
    width: 42px;
  }
  .content {
    display: block;
    width: 94%;
  }
}
.ant-form {
  margin-top: 16px;
  .btn {
    width: 130px;
  }
  .btn:first-child {
    margin-right: 10px;
  }
}
.description-count {
  color: rgba(0, 0, 0, 0.25);
}
</style>