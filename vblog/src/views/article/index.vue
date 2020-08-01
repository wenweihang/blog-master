<template>
  <div id="article">
    <div class="header-container">
      <h1>文章管理</h1>
      <a-form-model ref="searchForm" :model="searchForm">
        <a-form-model-item prop="searchValue">
          <a-input-search
            placeholder="文章题目"
            enter-button
            style="width: 300px"
            @search="onSearch"
          />
        </a-form-model-item>
      </a-form-model>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="articlesData"
        :loading="loading"
        :pagination="pagination"
        @change="handleTableChange"
        :scroll="{ x: 1300 }"
      >
        <template slot="titleSlot" slot-scope="text, record">
          <a-tag color="cyan">{{ record.name }}</a-tag>
          <span>{{ record.title }}</span>
        </template>
        <template slot="status" slot-scope="text, record">
          <a-switch v-model="record.status" @change="onStatusChange(record)" />
        </template>
        <template slot="allowComment" slot-scope="text, record">
          <a-switch
            v-model="record.allowComment"
            @change="onAllowCommentChange(record)"
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
          <a @click="onAssignCategory(record.id)">修改分类</a>
          <a-divider type="vertical" />
          <a @click="onEdit(record.id)">编辑</a>
          <a-divider type="vertical" />
          <a @click="onDelete(record)">删除</a>
        </template>
      </a-table>
    </div>
    <!-- 分配分类 -->
    <a-modal
      v-model="assignCategoryModalIsVisible"
      title="分配分类"
      @cancel="handleAssignCategoryCancel"
      @ok="handleAssignCategoryOk"
      cancelText="取消"
      okText="保存"
    >
      <a-form-model v-bind="assignCategoryLayout">
        <a-form-model-item label="分配分类">
          <a-select
            :value="currentArticleHasCategory"
            style="width: 300px"
            @change="handleAssignCategoryChange"
            placeholder="选择分类"
          >
            <a-select-option
              v-for="category in allCategory"
              :key="category.name"
            >
              {{ category.name }}
            </a-select-option>
          </a-select>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import {
  getArticles,
  updateArticle,
  deleteArticleById
} from '@/api/article'
import {
  getCategoryByArticleId,
  addCategoryForArticle
} from '@/api/articleCategoryRef'
import { getCategories } from '@/api/category'
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
    title: '标题',
    dataIndex: 'title',
    width: '320px',
    scopedSlots: { customRender: 'titleSlot' }
  },
  {
    title: '是否发布',
    dataIndex: 'status',
    width: '100px',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '是否评论',
    dataIndex: 'allowComment',
    width: '100px',
    scopedSlots: { customRender: 'allowComment' }
  },
  {
    title: '创建时间',
    dataIndex: 'newCreateTime'
  },
  {
    title: '更新时间',
    dataIndex: 'newLastUpdateTime'
  },
  {
    title: '权重',
    dataIndex: 'sort',
    width: '80px',
    align: 'right',
    scopedSlots: { customRender: 'sort' }
  },
  {
    title: '访问量',
    dataIndex: 'views',
    width: '100px',
    align: 'right',
    sorter: true
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' }
  }
]
export default {
  name: 'Article',
  created() {
    this.getArticlesData()
  },
  data() {
    return {
      searchForm: {
        searchValue: ''
      },
      articlesData: [],
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
      // 分配分类
      assignCategoryModalIsVisible: false,
      assignCategoryLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 }
      },
      currentAssignCategoryForArticleId: null,
      currentArticleHasCategory: '',
      allCategory: [],
      // 点击排序
      viewsOrder: ''
    }
  },
  methods: {
    onSearch(e) {
      this.searchForm.searchValue = e
      this.pagination.current = 1
      this.getArticlesData(e)
    },
    // 排序
    handleTableChange(pagination, filters, sorter) {
      if (sorter.field == 'views') {
        if (sorter.order == 'descend') {
          this.viewsOrder = 'desc'
        } else {
          this.viewsOrder = 'asc'
        }
      }
      let title = ''
      if (this.searchForm.searchValue != null) {
        title = this.searchForm.searchValue
      }
      this.getArticlesData(title)
    },
    getArticlesData(title) {
      this.loading = true
      getArticles(
        title,
        this.viewsOrder,
        this.pagination.current,
        this.pagination.pageSize
      ).then(response => {
        if (response && response.data) {
          const data = response.data
          const ariticles = data.list
          ariticles.forEach(article => {
            const newCreateTime = formatDate(article.createTime)
            const newLastUpdateTime = formatDate(article.lastUpdateTime)
            article.newCreateTime = newCreateTime
            article.newLastUpdateTime = newLastUpdateTime
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.articlesData = data.list
          this.loading = false
        }
      })
    },
    onStatusChange(record) {
      if (!record.status) {
        this.statusModalShow('你确定要把该文章放到草稿箱吗?', record, true)
      } else {
        this.statusModalShow('你确定要发布该文章吗?', record, true)
      }
    },
    statusModalShow(title, record, isStatus) {
      const that = this
      Modal.confirm({
        title: title,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          updateArticle(record).then(response => {
            if (response && response.data) {
              message.success('修改成功')
              that.getArticlesData()
            }
          })
        },
        onCancel() {
          if (isStatus) {
            record.status = !record.status
          } else {
            record.allowComment = !record.allowComment
          }
        }
      })
    },
    onAllowCommentChange(record) {
      if (record.allowComment) {
        this.statusModalShow('你确定要开启该文章的评论功能吗?', record, false)
      } else {
        this.statusModalShow('你确定要关闭该文章的评论功能吗?', record, false)
      }
    },
    onEdit(id) {
      this.$router.push({
        name: 'Write',
        params: {
          id
        }
      })
    },
    onDelete(record) {
      const that = this
      Modal.confirm({
        title: '删除文章',
        content: `你要删除标题为【${record.title}】的文章吗?`,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          deleteArticleById(record.id).then(response => {
            if (response && response.data) {
              message.success('删除成功')
              that.getArticlesData()
            }
          })
        }
      })
    },
    onSortChange(record) {
      const that = this
      updateArticle(record).then(response => {
        if (response && response.data) {
          message.success('修改成功')
          that.getArticlesData()
        }
      })
    },
    onPageChange(currentPage) {
      this.pagination.current = currentPage
      let title = ''
      if (this.searchForm.searchValue != null) {
        title = this.searchForm.searchValue
      }
      this.getArticlesData(title)
    },
    // 分配分类
    onAssignCategory(id) {
      this.currentAssignCategoryForArticleId = id
      // 获取所有分类
      getCategories().then(response => {
        if (response && response.data) {
          this.allCategory = response.data != null ? response.data.list : []
        }
      })
      // 获取当前文章所拥有分类
      getCategoryByArticleId(id).then(response => {
        if (response && response.data) {
          this.currentArticleHasCategory =
            response.data != null ? response.data.name : ''
        }
      })
      this.assignCategoryModalIsVisible = true
    },
    handleAssignCategoryChange(value) {
      this.currentArticleHasCategory = value
    },
    handleAssignCategoryOk() {
      const that = this
      addCategoryForArticle(
        this.currentAssignCategoryForArticleId,
        this.currentArticleHasCategory
      ).then(response => {
        if (response && response.data) {
          that.getArticlesData()
          this.assignCategoryModalIsVisible = false
          this.currentArticleHasCategory = ''
          message.success('分配分类成功')
        }
      })
    },
    handleAssignCategoryCancel() {
      this.assignCategoryModalIsVisible = false
      this.currentArticleHasCategory = ''
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
