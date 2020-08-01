<template>
  <div id="user">
    <div class="header-container">
      <h1>用户管理</h1>
      <a-form-model ref="searchForm" layout="inline" :model="searchForm">
        <a-form-model-item prop="searchValue">
          <a-input-search
            placeholder="用户名称"
            enter-button
            style="width: 300px"
            @search="onSearch"
          />
        </a-form-model-item>
        <a-form-model-item prop="searchValue">
          <a-button type="primary" @click="addUser">新增</a-button>
        </a-form-model-item>
      </a-form-model>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="userData"
        :loading="loading"
        :pagination="pagination"
        :scroll="{ x: 1400 }"
      >
        <template slot="avatar" slot-scope="text, record">
          <img class="user-avatar" :src="record.avatar" alt="user-avatar" />
        </template>
        <template slot="enabled" slot-scope="text, record">
          <a-switch
            v-model="record.enabled"
            @change="onEnabledChange(record)"
          />
        </template>
        <template slot="action" slot-scope="text, record">
          <a @click="onModifyPassword(record.id)">修改密码</a>
          <a-divider type="vertical" />
          <a @click="onAssignRole(record.id)">分配角色</a>
          <a-divider type="vertical" />
          <a @click="onEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="onDelete(record)">删除</a>
        </template>
      </a-table>
    </div>
    <a-modal
      v-model="modalIsVisible"
      :title="modalTitle"
      @cancel="handleModalCancel"
      @ok="handleModalOk"
      cancelText="取消"
      :okText="modalOkText"
    >
      <a-form-model
        ref="userForm"
        :model="userForm"
        :rules="userRules"
        v-bind="layout"
      >
        <a-form-model-item label="头像" prop="avatar">
          <a-upload
            name="file"
            :data="uploadAvatarData"
            :action="ossUploadUrl"
            listType="picture"
            :multiple="false"
            :beforeUpload="beforeUpload"
            @change="handleUploadImagesChange"
          >
            <a-button> <a-icon type="upload" /> upload </a-button>
          </a-upload>
        </a-form-model-item>
        <a-form-model-item has-feedback label="用户名" prop="username">
          <a-input
            v-model="userForm.username"
            type="text"
            placeholder="用户名"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item
          v-if="modalIsEdit == false"
          label="密码"
          prop="password"
        >
          <a-input
            v-model="userForm.password"
            type="password"
            placeholder="密码"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item
          v-if="modalIsEdit == false"
          label="再次输入密码"
          prop="checkPassword"
        >
          <a-input
            v-model="userForm.checkPassword"
            type="password"
            placeholder="再次输入密码"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item label="昵称" prop="nickname">
          <a-input
            v-model="userForm.nickname"
            type="text"
            placeholder="昵称"
            :maxLength="10"
          />
        </a-form-model-item>
        <a-form-model-item label="邮箱" prop="email">
          <a-input
            v-model="userForm.email"
            type="text"
            placeholder="邮箱"
            :maxLength="30"
          />
        </a-form-model-item>
        <a-form-model-item label="是否启用" prop="enabled">
          <a-switch v-model="userForm.enabled" />
        </a-form-model-item>
        <a-form-model-item label="简介" prop="intro">
          <a-textarea
            placeholder="输入个人简介"
            v-model="userForm.intro"
            :autoSize="{ minRows: 4, maxRows: 4 }"
            :maxLength="200"
          />
          <span class="description-count"
            >{{ userForm.intro != null ? userForm.intro.length : 0 }}/200</span
          >
        </a-form-model-item>
      </a-form-model>
    </a-modal>

    <!-- 修改密码 -->
    <a-modal
      v-model="passwordModalIsVisible"
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
        v-bind="layout"
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

    <!-- 分配角色 -->
    <a-modal
      v-model="assignRoleModalIsVisible"
      title="分配角色"
      @cancel="handleAssignRoleCancel"
      @ok="handleAssignRoleOk"
      cancelText="取消"
      okText="保存"
    >
      <a-form-model v-bind="assignRoleLayout">
        <a-form-model-item label="分配角色">
          <a-select
            :value="currentUserHasRole"
            style="width: 300px"
            @change="handleAssignRoleChange"
            placeholder="选择角色"
          >
            <a-select-option v-for="role in allRoles" :key="role.name">
              {{ role.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {
  getUsers,
  searchUsers,
  saveUser,
  updateUser,
  updateUserPassword,
  deleteUserById
} from '@/api/user'
import { getRoleByUserId, addRoleForUser } from '@/api/userRoleRef'
import { getRoles } from '@/api/role'
import { formatDate } from '@/utils/date'
import { message, Modal } from 'ant-design-vue'
import { getpolicy } from '@/api/oss'
import { getCookie } from '@/utils/support'

const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    width: '60px',
    align: 'center'
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    width: '90px',
    align: 'center',
    scopedSlots: { customRender: 'avatar' }
  },
  {
    title: '用户名',
    dataIndex: 'username',
    width: '160px'
  },
  {
    title: '昵称',
    dataIndex: 'nickname',
    width: '130px'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    width: '180px'
  },
  {
    title: '是否启用',
    dataIndex: 'enabled',
    width: '100px',
    scopedSlots: { customRender: 'enabled' }
  },
  {
    title: '创建时间',
    dataIndex: 'newCreateTime',
    width: '200px'
  },
  {
    title: '最后登录',
    dataIndex: 'newLastLoginTime',
    width: '200px'
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' }
  }
]
export default {
  name: 'User',
  created() {
    const roles = JSON.parse(getCookie('roles'))
    // 判断是否有权限
    if (roles[0] == 'ROLE_USER') {
      this.$router.replace('403')
    } else {
      this.getUserData()
    }
  },
  data() {
    // 验证分类名称的唯一性
    const validateUsername = (rule, value, callback) => {
      if (value.length <= 0) return callback(new Error('用户名不能为空'))
      const isEdit = this.modalIsEdit
      getUsers(value).then(response => {
        if (response && response.data) {
          const total = response.data.total
          if (total > 0) {
            if (
              (isEdit &&
                value.toLowerCase() != this.currentEditUsername.toLowerCase()) ||
              !isEdit
            ) {
              return callback(new Error('用户名已存在'))
            } else {
              return callback()
            }
          } else {
            return callback()
          }
        }
      })
    }
    // 验证密码是否一致
    const validatePassword = (rule, value, callback) => {
      if (value != this.userForm.password) {
        return callback(new Error('两次密码不一致'))
      } else {
        return callback()
      }
    }
    // 修改密码: 验证新密码
    const validateNewPassword = (rule, value, callback) => {
      if (value != this.passwordForm.newPassword) {
        return callback(new Error('两次密码不一致'))
      } else {
        return callback()
      }
    }
    return {
      searchForm: {
        searchValue: ''
      },
      userData: [],
      loading: false,
      columns,
      pagination: {
        // 分页属性
        current: 1, // 当前页数
        total: 0,
        pageSize: 6,
        showTotal: total => `共有${total}条数据`,
        onChange: this.onPageChange.bind(this) // 点击页码触发
      },
      modalOkText: '添加',
      modalIsEdit: false,
      modalIsVisible: false,
      modalTitle: '添加用户',
      layout: {
        labelCol: { span: 6 },
        wrapperCol: { span: 14 }
      },
      defaultUserForm: {
        avatar: '',
        username: '',
        password: '',
        checkPassword: '',
        nickname: '',
        email: '',
        intro: '',
        enabled: true
      },
      userForm: {
        avatar: '',
        username: '',
        password: '',
        checkPassword: '',
        nickname: '',
        email: '',
        intro: '',
        enabled: true
      },
      userRules: {
        username: [
          { required: true, validator: validateUsername, trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '用户名的长度要在2到20之间',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            min: 2,
            max: 20,
            message: '密码的长度要在2到20之间',
            trigger: 'blur'
          }
        ],
        checkPassword: [
          { required: true, validator: validatePassword, trigger: 'blur' }
        ],
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
      currentEditUsername: '',
      ossUploadUrl: 'http://zoe-oss.oss-cn-beijing.aliyuncs.com',
      uploadAvatarData: {
        policy: '',
        signature: '',
        key: '',
        ossaccessKeyId: '',
        dir: '',
        host: ''
        // callback:'',
      },
      // 修改密码
      passwordModalIsVisible: false,
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
      // 分配角色
      assignRoleModalIsVisible: false,
      assignRoleLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 }
      },
      currentAssignRoleForUserId: null,
      currentUserHasRole: '',
      allRoles: []
    }
  },
  methods: {
    onSearch(e) {
      this.searchForm.searchValue = e
      this.pagination.current = 1
      this.searchUserData()
    },
    searchUserData() {
      this.loading = true
      searchUsers(
        this.searchForm.searchValue,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        if (response && response.data) {
          const data = response.data
          const users = data.list
          users.forEach(user => {
            const newCreateTime = formatDate(user.createTime)
            const newLastLoginTime = formatDate(user.lastLoginTime)
            user.newCreateTime = newCreateTime
            user.newLastLoginTime = newLastLoginTime
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.userData = users
          this.loading = false
        }
      })
    },
    getUserData(username) {
      this.loading = true
      getUsers(
        username,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        if (response && response.data) {
          const data = response.data
          const users = data.list
          users.forEach(user => {
            const newCreateTime = formatDate(user.createTime)
            const newLastLoginTime = formatDate(user.lastLoginTime)
            user.newCreateTime = newCreateTime
            user.newLastLoginTime = newLastLoginTime
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.userData = users
          this.loading = false
        }
      })
    },
    onEdit(record) {
      this.modalIsVisible = true
      this.modalIsEdit = true
      this.modalTitle = '编辑角色'
      this.modalOkText = '保存'
      this.currentEditUsername = record.username
      this.userForm = JSON.parse(JSON.stringify(record))
      this.getPolicyData()
    },
    onDelete(record) {
      const that = this
      Modal.confirm({
        title: '删除用户',
        content: `你要删除【${record.username}】用户吗?`,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          deleteUserById(record.id).then(response => {
            if (response.code === 200) {
              message.success('删除成功')
              that.getUserData()
            }
          })
        }
      })
    },
    onPageChange(currentPage) {
      this.pagination.current = currentPage
      if (this.searchForm.searchValue != null) {
        this.searchUserData()
      } else {
        this.getUserData()
      }
    },
    addUser() {
      this.modalIsVisible = true
      this.modalIsEdit = false
      this.modalTitle = '添加用户'
      this.modalOkText = '添加'
      this.getPolicyData()
    },
    handleModalOk() {
      const that = this
      this.$refs.userForm.validate(valid => {
        if (valid) {
          if (!this.modalIsEdit) {
            saveUser(this.userForm).then(response => {
              if (response && response.data) {
                message.success('添加成功')
                that.getUserData()
                that.modalIsVisible = false
                that.$refs.userForm.resetFields()
                that.userForm = JSON.parse(JSON.stringify(this.defaultUserForm))
              }
            })
          } else {
            updateUser(this.userForm).then(response => {
              if (response && response.data) {
                message.success('修改成功')
                that.getUserData()
                that.modalIsVisible = false
                that.$refs.userForm.resetFields()
                that.userForm = JSON.parse(JSON.stringify(this.defaultUserForm))
              }
            })
          }
        } else {
          return false
        }
      })
    },
    handleModalCancel() {
      this.modalIsVisible = false
      this.$refs.userForm.resetFields()
      this.userForm = JSON.parse(JSON.stringify(this.defaultUserForm))
    },
    onEnabledChange(record) {
      if (record.enabled) {
        this.enableModalShow('你确定启用该用户吗?', record)
      } else {
        this.enableModalShow('你确定禁用该用户吗?', record)
      }
    },
    enableModalShow(title, record) {
      const that = this
      Modal.confirm({
        title: title,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          updateUser(record).then(response => {
            if (response && response.data) {
              message.success('修改成功')
              that.getUserData()
            }
          })
        },
        onCancel() {
          record.enabled = !record.enabled
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
        this.userForm.avatar = imageUrl
        message.success('头像上传成功')
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
    // 修改密码
    onModifyPassword(id) {
      this.passwordModalIsVisible = true
      this.passwordForm.id = id
    },
    handleModifyPasswordOk() {
      this.$refs.passwordForm.validate(valid => {
        if (valid) {
          updateUserPassword(
            this.passwordForm.id,
            this.passwordForm.rawPassword,
            this.passwordForm.newPassword
          ).then(response => {
            if (response && response.data) {
              this.passwordModalIsVisible = false
              this.$refs.passwordForm.resetFields()
              message.success('修改密码成功')
            }
          })
        } else {
          return false
        }
      })
    },
    handleModifyPasswordCancel() {
      this.$refs.passwordForm.resetFields()
    },
    // 分配角色
    onAssignRole(id) {
      this.currentAssignRoleForUserId = id
      // 获取所有角色
      getRoles().then(response => {
        if (response && response.data) {
          this.allRoles = response.data != null ? response.data.list : []
        }
      })
      // 获取当前用户所拥有的角色
      getRoleByUserId(id).then(response => {
        if (response && response.data) {
          this.currentUserHasRole =
            response.data != null ? response.data.name : ''
        }
      })
      this.assignRoleModalIsVisible = true
    },
    handleAssignRoleChange(value) {
      this.currentUserHasRole = value
    },
    handleAssignRoleOk() {
      addRoleForUser(
        this.currentAssignRoleForUserId,
        this.currentUserHasRole
      ).then(response => {
        if (response && response.data) {
          this.assignRoleModalIsVisible = false
          this.currentUserHasRole = ''
          message.success('分配角色成功')
        }
      })
    },
    handleAssignRoleCancel() {
      this.assignRoleModalIsVisible = false
      this.currentUserHasRoles = []
    }
  }
}
</script>

<style scoped lang="scss">
.description-count {
  color: rgba(0, 0, 0, 0.25);
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
}
</style>
