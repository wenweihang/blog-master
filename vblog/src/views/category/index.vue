<template>
  <div id="category">
    <div class="header-container">
      <h1>分类管理</h1>
      <a-form-model ref="searchForm" layout="inline" :model="searchForm">
        <a-form-model-item prop="searchValue">
          <a-input-search
            placeholder="分类名称"
            enter-button
            style="width: 300px"
            @search="onSearch"
          />
        </a-form-model-item>
        <a-form-model-item prop="searchValue">
          <a-button type="primary" @click="addCategory">新增</a-button>
        </a-form-model-item>
      </a-form-model>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="categoryDate"
        :loading="loading"
        :pagination="pagination"
        :scroll="{ x: 860 }"
      >
        <template slot="enabled" slot-scope="text, record">
          <a-switch
            v-model="record.enabled"
            @change="onEnabledChange(record)"
          />
        </template>
        <template slot="sort" slot-scope="text, record">
          <a-input
            class="sort-input"
            v-model="record.sort"
            @blur="onSortChange(record)"
          />
        </template>
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
        ref="categoryForm"
        :model="categoryForm"
        :rules="categoryRules"
        v-bind="layout"
      >
        <a-form-model-item has-feedback label="分类名称" prop="name">
          <a-input
            v-model="categoryForm.name"
            type="text"
            placeholder="分类名称"
            :maxLength="20"
          />
        </a-form-model-item>
        <a-form-model-item label="权重" prop="sort">
          <a-input-number
            v-model.number="categoryForm.sort"
            placeholder="权重"
            :min="1"
            :max="100"
          />
        </a-form-model-item>
        <a-form-model-item label="是否启用" prop="enabled">
          <a-switch v-model="categoryForm.enabled" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {
  getCategories,
  searchCategory,
  saveCategory,
  updateCategory,
  deleteCategoryById
} from '@/api/category'
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
    title: '分类名称',
    dataIndex: 'name',
    width: '280px'
  },
  {
    title: '是否启用',
    dataIndex: 'enabled',
    width: '100px',
    scopedSlots: { customRender: 'enabled' }
  },
  {
    title: '创建时间',
    dataIndex: 'newCreateTime'
  },
  {
    title: '权重',
    dataIndex: 'sort',
    width: '80px',
    align: 'right',
    scopedSlots: { customRender: 'sort' }
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' }
  }
]
export default {
  name: 'Category',
  created() {
    this.getCategoryDate()
  },
  data() {
    // 验证分类名称的唯一性
    const validateName = (rule, value, callback) => {
      if (value.length <= 0) return callback(new Error('分类名称不能为空'))
      const isEdit = this.modalIsEdit
      getCategories(value).then(response => {
        if (response && response.data) {
          const total = response.data.total
          if (total > 0) {
            if (
              (isEdit &&
                value.toLowerCase() != this.currentEditName.toLowerCase()) ||
              !isEdit
            ) {
              return callback(new Error('分类名称已存在'))
            } else {
              return callback()
            }
          } else {
            return callback()
          }
        }
      })
    }
    return {
      searchForm: {
        searchValue: ''
      },
      categoryDate: [],
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
      categoryForm: {
        name: '',
        enabled: true,
        sort: 1
      },
      categoryRules: {
        name: [
          { required: true, validator: validateName, trigger: 'blur' },
          {
            required: true,
            min: 2,
            max: 20,
            message: '分类名称的长度要在2到20之间',
            trigger: 'blur'
          }
        ],
        sort: [
          {
            required: true,
            message: '权重不能为空',
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
      this.searchCategoryDate()
    },
    searchCategoryDate() {
      this.loading = true
      searchCategory(
        this.searchForm.searchValue,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        if (response && response.data) {
          const data = response.data
          const categories = data.list
          categories.forEach(category => {
            const newCreateTime = formatDate(category.createTime)
            category.newCreateTime = newCreateTime
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.categoryDate = data.list
          this.loading = false
        }
      })
    },
    getCategoryDate(title) {
      this.loading = true
      getCategories(
        title,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        if (response && response.data) {
          const data = response.data
          const categories = data.list
          categories.forEach(category => {
            const newCreateTime = formatDate(category.createTime)
            category.newCreateTime = newCreateTime
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.categoryDate = data.list
          this.loading = false
        }
      })
    },
    onEnabledChange(record) {
      if (record.enabled) {
        this.enableModalShow('你确定启用该分类吗?', record)
      } else {
        this.enableModalShow('你确定禁用该分类吗?', record)
      }
    },
    enableModalShow(title, record) {
      const that = this
      Modal.confirm({
        title: title,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          updateCategory(record).then(response => {
            if (response && response.data) {
              message.success('修改成功')
              that.getCategoryDate()
            }
          })
        },
        onCancel() {
          record.enabled = !record.enabled
        }
      })
    },
    onEdit(record) {
      this.modalIsVisible = true
      this.modalIsEdit = true
      this.modalTitle = '编辑分类'
      this.modalOkText = '保存'
      this.currentEditName = record.name
      this.categoryForm = JSON.parse(JSON.stringify(record))
    },
    onDelete(record) {
      const that = this
      Modal.confirm({
        title: '删除分类',
        content: `你要删除【${record.name}】分类吗?`,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          deleteCategoryById(record.id).then(response => {
            if (response && response.data) {
              message.success('删除成功')
              that.getCategoryDate()
            }
          })
        }
      })
    },
    onSortChange(record) {
      const that = this
      updateCategory(record).then(response => {
        if (response && response.data) {
          message.success('修改成功')
          that.getCategoryDate()
        }
      })
    },
    onPageChange(currentPage) {
      this.pagination.current = currentPage
      if (this.searchForm.searchValue != null) {
        this.searchCategoryDate()
      } else {
        this.getCategoryDate()
      }
    },
    addCategory() {
      this.modalIsVisible = true
      this.modalIsEdit = false
      this.modalTitle = '添加分类'
      this.modalOkText = '添加'
    },
    handleModalOk() {
      this.$refs.categoryForm.validate(valid => {
        if (valid) {
          if (!this.modalIsEdit) {
            saveCategory(this.categoryForm).then(response => {
              if (response && response.data) {
                this.getCategoryDate()
                this.modalIsVisible = false
                this.$refs.categoryForm.resetFields()
                this.categoryForm = {
                  name: '',
                  enabled: true,
                  sort: 1
                }
                message.success('添加成功')
              }
            })
          } else {
            updateCategory(this.categoryForm).then(response => {
              if (response && response.data) {
                this.getCategoryDate()
                this.modalIsVisible = false
                this.$refs.categoryForm.resetFields()
                this.categoryForm = {
                  name: '',
                  enabled: true,
                  sort: 1
                }
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
      this.$refs.categoryForm.resetFields()
      this.categoryForm = {
        name: '',
        enabled: true,
        sort: 1
      }
    }
  }
}
</script>

<style scoped lang="scss">
.sort-input {
  border: 0;
  background: transparent;
}
</style>
