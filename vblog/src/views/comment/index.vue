<template>
  <div id="comment">
    <div class="header-container">
      <h1>评论管理</h1>
    </div>
    <div class="content-container">
      <a-table
        :columns="columns"
        :loading="loading"
        :pagination="pagination"
        :row-key="record => record.id"
        :data-source="commentDate"
      >
        <template slot="content" slot-scope="text, record">
          <a-tag v-if="record.isReply" color="blue">
            已回复
          </a-tag>
          <a-tag v-else color="red">
            未回复
          </a-tag>
          <span>{{ record.content }}</span>
        </template>
        <template slot="enabled" slot-scope="text, record">
          <a-switch
            v-model="record.enabled"
            @change="onEnabledChange(record)"
          />
        </template>
        <template
          v-if="record.isReply"
          slot="expandedRowRender"
          slot-scope="record"
          style="margin: 0"
          :row-key="record => record.reply.id"
        >
          <a-tag color="blue">
            回复
          </a-tag>
          <span>{{ record.reply.content }}</span>
        </template>
        <template slot="action" slot-scope="text, record">
          <a v-if="record.isReply" @click="onEditReply(record)">修改回复</a>
          <a v-else @click="onReply(record)">回复</a>
          <a-divider type="vertical" />
          <a v-if="record.isReply" @click="onDeleteReplyById(record.reply.id)"
            >删除回复</a
          >
          <a-divider v-if="record.isReply" type="vertical" />
          <a @click="onDelete(record)">删除</a>
        </template>
      </a-table>
    </div>
    <a-modal
      v-model="modifyReplyVisible"
      :title="modifyReplyTitle"
      okText="保存"
      cancelText="取消"
      @ok="handleReplyOk"
      @cancel="handleReplyCancel"
    >
      <a-form-model
        ref="replyForm"
        :model="replyForm"
        :rules="replyrRules"
        v-bind="replyLayout"
      >
        <a-form-model-item label="回复内容" prop="content">
          <a-textarea
            v-model="replyForm.content"
            placeholder="输入回复内容"
            :auto-size="{ minRows: 6, maxRows: 6 }"
            :maxLength="200"
          />
          <span class="description-count"
            >{{
              replyForm.content != null ? replyForm.content.length : 0
            }}/200</span
          >
        </a-form-model-item>
        <a-form-model-item label="是否显示" prop="enabled">
          <a-switch v-model="replyForm.enabled" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>

<script>
import { getComments, updateEnabled, deleteCommentById } from '@/api/comment'
import { saveReply, updateReply, deleteReplyById } from '@/api/reply'
import { formatDate } from '@/utils/date'
import { message, Modal } from 'ant-design-vue'

const columns = [
  {
    title: '文章标题',
    dataIndex: 'article.title',
    width: '220px'
  },
  {
    title: '评论内容',
    dataIndex: 'content',
    width: '280px',
    scopedSlots: { customRender: 'content' }
  },
  {
    title: '访客昵称',
    dataIndex: 'visitorNickname',
    width: '120px'
  },
  {
    title: '访客邮箱',
    dataIndex: 'visitorEmail',
    width: '180px'
  },
  {
    title: '是否显示',
    dataIndex: 'enabled',
    width: '100px',
    scopedSlots: { customRender: 'enabled' }
  },
  {
    title: '发表时间',
    dataIndex: 'newCreateTime',
    width: '190px'
  },
  {
    title: '操作',
    key: 'action',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: 'Comment',
  mounted() {
    this.getCommentDate()
  },
  data() {
    const validateContent = (rule, value, callback) => {
      if (value.length <= 0) {
        return callback(new Error('回复内容不能为空'))
      } else {
        return callback()
      }
    }
    return {
      columns,
      commentDate: [],
      loading: false,
      pagination: {
        // 分页属性
        current: 1, // 当前页数
        total: 0,
        pageSize: 6,
        showTotal: total => `共有${total}条数据`,
        onChange: this.onPageChange.bind(this) // 点击页码触发
      },
      modifyReplyVisible: false,
      modifyReplyTitle: '修改回复',
      replyLayout: {
        labelCol: { span: 4 },
        wrapperCol: { span: 14 }
      },
      replyForm: {
        content: '',
        enabled: true
      },
      replyrRules: {
        content: [
          {
            required: true,
            validator: validateContent,
            trigger: 'blur'
          }
        ]
      },
      currentCommentData: []
    }
  },
  methods: {
    getCommentDate() {
      this.loading = true
      getComments(this.pagination.current, this.pagination.pageSize).then(
        response => {
          const data = response.data
          const comments = data.list
          comments.forEach(comment => {
            const newCreateTime = formatDate(comment.createTime)
            comment.newCreateTime = newCreateTime
            if (comment.reply) {
              comment.isReply = true
              const newReplyCreateTime = formatDate(comment.reply.createTime)
              comment.reply.newCreateTime = newReplyCreateTime
            } else {
              comment.isReply = false
            }
          })
          // 更新分页的数据总数
          this.pagination.total = data.total
          this.commentDate = comments
          this.loading = false
        }
      )
    },
    // 回复
    onReply(record) {
      this.modifyReplyVisible = true
      this.modifyReplyTitle = '回复'
      this.currentCommentData = record
    },
    // 修改回复
    onEditReply(record) {
      this.replyForm = record.reply
      this.modifyReplyVisible = true
      this.modifyReplyTitle = '修改回复'
      this.currentCommentData = record
    },
    handleReplyOk() {
      const that = this
      const comment = this.currentCommentData
      this.$refs.replyForm.validate(valid => {
        if (valid) {
          if (comment.isReply) {
            updateReply(comment.reply).then(response => {
              if (response && response.data) {
                message.success('回复修改成功')
                this.modifyReplyVisible = false
                this.$refs.replyForm.resetFields()
                this.replyForm = {
                  content: '',
                  enabled: true
                }
              }
              that.getCommentDate()
            })
          } else {
            saveReply(
              this.replyForm.content,
              this.replyForm.enabled,
              comment.id
            ).then(response => {
              if (response && response.data) {
                message.success('回复评论成功')
                this.modifyReplyVisible = false
                this.$refs.replyForm.resetFields()
                this.replyForm = {
                  content: '',
                  enabled: true
                }
                that.getCommentDate()
              }
            })
          }
        } else {
          return false
        }
      })
    },
    handleReplyCancel() {
      this.$refs.replyForm.resetFields()
      this.replyForm = {
        content: '',
        enabled: true
      }
    },
    onEnabledChange(record) {
      if (record.enabled) {
        this.enableModalShow('你确定显示该评论吗?', record)
      } else {
        this.enableModalShow('你确定不显示该评论吗?', record)
      }
    },
    enableModalShow(title, record) {
      const that = this
      Modal.confirm({
        title: title,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          updateEnabled(record.id, record.enabled).then(response => {
            if (response && response.data) {
              message.success('修改成功')
              that.getCommentDate()
            }
          })
        },
        onCancel() {
          record.enabled = !record.enabled
        }
      })
    },
    onDelete(record) {
      const that = this
      Modal.confirm({
        title: '删除评论',
        content: `你要删除【${record.visitorNickname}】发表的评论吗?`,
        okText: '确定',
        cancelText: '取消',
        onOk() {
          deleteCommentById(record.id).then(response => {
            if (response && response.data) {
              message.success('删除成功')
              that.getCommentDate()
            }
          })
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
    // 删除回复
    onDeleteReplyById(id) {
      const that = this
      deleteReplyById(id).then(response => {
        if (response && response.data) {
          message.success('回复删除成功')
          that.getCommentDate()
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.description-count {
  color: rgba(0, 0, 0, 0.25);
}
</style>
