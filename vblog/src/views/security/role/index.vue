<template>
  <div id="role">
    <div class="header-container">
      <h1>角色管理</h1>
      <a-form-model ref="searchForm" layout="inline" :model="searchForm">
        <a-form-model-item prop="searchValue">
          <a-input-search
            placeholder="角色名称"
            enter-button
            style="width: 300px"
            @search="onSearch"
          />
        </a-form-model-item>
        <a-form-model-item prop="searchValue">
          <a-button type="primary" @click="addRole">新增</a-button>
        </a-form-model-item>
      </a-form-model>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="roleDate"
        :loading="loading"
        :pagination="pagination"
        :scroll="{ x: 800 }"
      >
        <template slot="action" slot-scope="text, record">
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
        ref="roleForm"
        :model="roleForm"
        :rules="roleRules"
        v-bind="layout"
      >
        <a-form-model-item has-feedback label="分类名称" prop="name">
          <a-input
            v-model="roleForm.name"
            type="text"
            placeholder="角色名称"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item label="描述" prop="description">
          <a-textarea
            placeholder="输入描述"
            v-model="roleForm.description"
            :autoSize="{ minRows: 4, maxRows: 4 }"
            :maxLength="50"
          />
          <span class="description-count"
            >{{ roleForm.description.length }}/50</span
          >
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {
  getRoles,
  searchRoles,
  saveRole,
  updateRole,
  deleteRoleById
} from '@/api/role'
import { formatDate } from '@/utils/date'
import { message, Modal } from 'ant-design-vue'

const columns = [
  {
    title: '序号',
    dataIndex: 'id',
    width: '60px',
    align: 'center'
  },
  {
    title: '角色名称',
    dataIndex: 'name',
    width: '200px'
  },
  {
    title: '描述',
    dataIndex: 'description',
    width: '320px'
  },
  {
    title: '创建时间',
    dataIndex: 'newCreateTime'
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' }
  }
]
export default {
  name: 'Role',
  created() {
    this.getRoleDate()
  },
  data() {
    // 验证分类名称的唯一性
    const validateName = (rule, value, callback) => {
      if (value.length <= 0) return callback(new Error('角色名称不能为空'))
      const isEdit = this.modalIsEdit
      getRoles(value).then(response => {
        const total = response.data.total
        if (total > 0) {
          if (
            (isEdit &&
              value.toLowerCase() != this.currentEditName.toLowerCase()) ||
            !isEdit
          ) {
            return callback(new Error('角色名称已存在'))
          } else {
            return callback()
          }
        } else {
          return callback()
        }
      })
    }
    return {
      searchForm: {
        searchValue: ''
      },
      roleDate: [],
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
      modalTitle: '添加分类',
      layout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 }
      },
      roleForm: {
        name: '',
        description: ''
      },
      roleRules: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' },
          {
            required: true,
            min: 2,
            max: 20,
            message: '角色名称的长度要在2到20之间',
            trigger: 'blur'
          }
        ]
      },
      currentEditName: ''
    }
  },
  methods: {
    onSearch(e) {
      this.searchForm.searchValue = e
      this.pagination.current = 1
      this.searchRoleDate()
    },
    searchRoleDate() {
      this.loading = true
      searchRoles(
        this.searchForm.searchValue,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        const data = response.data
        const roles = data.list
        roles.forEach(role => {
          const newCreateTime = formatDate(role.createTime)
          role.newCreateTime = newCreateTime
        })
        // 更新分页的数据总数
        this.pagination.total = data.total
        this.roleDate = roles
        this.loading = false
      })
    },
    getRoleDate(name) {
      this.loading = true
      getRoles(name, this.pagination.current, this.pagination.pageSize).then(
        response => {
          if (response && response.data) {
            const data = response.data
            const roles = data.list
            roles.forEach(role => {
              const newCreateTime = formatDate(role.createTime)
              role.newCreateTime = newCreateTime
            })
            // 更新分页的数据总数
            this.pagination.total = data.total
            this.roleDate = roles
            this.loading = false
          }
        }
      )
    },
    onEdit(record) {
      this.modalIsVisible = true
      this.modalIsEdit = true
      this.modalTitle = '编辑角色'
      this.modalOkText = '保存'
      this.currentEditName = record.name
      this.roleForm = JSON.parse(JSON.stringify(record))
    },
    onDelete(record) {
      const that = this
      Modal.confirm({
        title: '删除角色',
        content: `你要删除【${record.name}】角色吗?`,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          deleteRoleById(record.id).then(response => {
            message.success('删除成功')
            that.getRoleDate()
          })
        }
      })
    },
    onPageChange(currentPage) {
      this.pagination.current = currentPage
      if (this.searchForm.searchValue != null) {
        this.searchRoleDate()
      } else {
        this.getRoleDate()
      }
    },
    addRole() {
      this.modalIsVisible = true
      this.modalIsEdit = false
      this.modalTitle = '添加角色'
      this.modalOkText = '添加'
    },
    handleModalOk() {
      this.$refs.roleForm.validate(valid => {
        if (valid) {
          if (!this.modalIsEdit) {
            saveRole(this.roleForm).then(response => {
              if (response && response.data) {
                this.getRoleDate()
                this.modalIsVisible = false
                this.$refs.roleForm.resetFields()
                message.success('添加成功')
              }
            })
          } else {
            updateRole(this.roleForm).then(response => {
              if (response && response.data) {
                this.getRoleDate()
                this.modalIsVisible = false
                this.$refs.roleForm.resetFields()
                message.success('修改成功')
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
      this.$refs.roleForm.resetFields()
    }
  }
}
</script>

<style scoped lang="scss">
.description-count {
  color: rgba(0, 0, 0, 0.25);
}
</style>
