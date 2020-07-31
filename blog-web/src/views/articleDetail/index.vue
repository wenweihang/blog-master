<template>
  <div class="article-detail">
      <div class="header-box">
        <div class="return-box" @click="onReturn">
          <a-icon type="left" />
          <span class="return-label">返回</span>
        </div>
      </div>
      <div class="article-box">
        <div class="article-content-box">
          <a-skeleton :loading="loading" :active="true">
            <h1>{{ article.title }}</h1>
            <div class="article-info-box">
              <a-tag color="red">{{ article.name }}</a-tag>
              <span class="date">发布时间: {{ article.newCreateTime }}</span>
              <span class="date">更新时间: {{ article.newLastUpdateTime }}</span>
              <span class="read-count">
                <a-icon type="eye" theme="filled" style="color: #ccccd7"/>
                {{ article.views }}
              </span>
            </div>
          </a-skeleton>
          <a-skeleton :loading="loading" :active="true">
            <div class="article-content markdown-body" v-html="article.content">
            </div>
          </a-skeleton>
        </div>
        <a-skeleton v-if="article.allowComment" :loading="loading" active>
          <div class="comment-box">
            <div class="comment-content-box">
              <h4>评论({{ commentTotal }})</h4>
              <template v-if="commentTotal > 0">
                <a-comment v-for="comment in commentData" :key="comment.id">
                  <p slot="author">
                    <span class="author">{{ comment.visitorNickname }}</span>
                    <span class="date">{{ comment.newCreateTime }}</span>
                  </p>
                  <p slot="content" class="comment-content">{{ comment.content }}</p>
                  <a-comment v-if="comment.isReply">
                    <p slot="author">
                      <span class="author">博主</span>
                      <span class="date">{{ comment.reply.newCreateTime }}</span>
                    </p>
                    <p slot="content" class="comment-content">{{ comment.reply.content }}</p>
                  </a-comment>
                </a-comment>
              </template>
              <div v-else class="data-kong">
                <h5>童鞋，木有找到相关内容~</h5>
                <p>快来抢沙发吧!</p>
              </div>
            </div>
            <div class="publish-comment-box">
              <h4>发表评论</h4>
              <a-form-model ref="commentForm" :model="commentForm" :rules="commentRules" v-bind="commentLayout">
              <a-form-model-item label="昵称" prop="visitorNickname">
                <a-input v-model="commentForm.visitorNickname" placeholder="昵称" :maxLength="10"/>
              </a-form-model-item>
              <a-form-model-item label="邮箱" prop="visitorEmail">
                <a-input v-model="commentForm.visitorEmail" placeholder="邮箱" :maxLength="30"/>
              </a-form-model-item>
              <a-form-model-item label="评论内容" prop="content">
                <a-textarea
                  v-model="commentForm.content"
                  placeholder="输入评论内容"
                  :maxLength="200"
                  :auto-size="{ minRows: 6, maxRows: 6 }"
                />
                <span class="description-count">{{ commentForm.content ? commentForm.content.length : 0 }}/200</span>
              </a-form-model-item>
              <a-form-model-item :wrapper-col="{ span: 14, offset: 3 }">
                <a-button class="reset-btn btn" @click="onReset">重置</a-button>
                <a-button class="btn" type="primary" @click="onPublishComment">发表评论</a-button>
              </a-form-model-item>
            </a-form-model>
            </div>
          </div>
        </a-skeleton>
      </div>
  </div>
</template>

<script>
import { getArticleById } from '@/api/article'
import { saveComment, getCommentsByArticleId } from '@/api/comment'
import { formatDate, convertDate } from '@/utils/date'
import { escapeStringToHtml, escapeHtmlToString } from '@/utils/escapeStringAndHtml'
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'
import '@/styles/github-markdown.scss'

// 代码高亮
const highlightCode = () => {
  const preEl = document.querySelectorAll('pre')
  preEl.forEach(el => {
    hljs.highlightBlock(el)
  })
}

export default {
  mounted() {
    this.currentArticleId = this.$route.params.id
    this.getArticle()
    this.getCommentData()
    highlightCode()
  },
  updated() {
    highlightCode()
  },
  data() {
    return {
      currentArticleId: -1,
      article: {},
      loading: false,
      commentLayout: {
        labelCol: { span: 3 },
        wrapperCol: { span: 14 }
      },
      commentForm: {
        visitorNickname: '',
        visitorEmail: '',
        content: ''
      },
      commentRules: {
        visitorNickname: [
          {
            required: true,
            message: '昵称不能为空',
            trigger: 'blur'
          }
        ],
        visitorEmail: [
          {
            required: true,
            max: 30,
            message: '邮箱的长度不能超过30个字符',
            trigger: 'blur'
          },
          {
            pattern: '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$',
            message: '邮箱格式不正确',
            trigger: 'blur'
          }
        ],
        content: [
          {
            required: true,
            message: '评论内容不能为空',
            trigger: 'blur'
          }
        ]
      },
      commentData: [],
      commentTotal: 0
    }
  },
  methods: {
    getArticle() {
      this.loading = true
      getArticleById(this.currentArticleId).then(response => {
        if (response && response.data) {
          const article = response.data
          article.newCreateTime = formatDate(article.createTime)
          article.newLastUpdateTime = formatDate(article.lastUpdateTime)
          article.content = escapeStringToHtml(article.content)
          this.article = article
          this.loading = false
        }
      })
    },
    onReturn() {
      this.$router.replace('/')
    },
    onReset() {
      this.$refs.commentForm.resetFields()
    },
    onPublishComment() {
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          const { visitorNickname, visitorEmail, content } = this.commentForm
          saveComment(
            visitorNickname,
            visitorEmail,
            content,
            this.currentArticleId
          ).then(response => {
            if (response) {
              this.getCommentData()
              this.$message.success('发表评论成功')
              this.$refs.commentForm.resetFields()
            }
          })
        } else {
          return false
        }
      })
    },
    getCommentData() {
      getCommentsByArticleId(this.currentArticleId).then(response => {
        if (response && response.data) {
          const data = response.data
          const comments = data.list
          comments.forEach(comment => {
            comment.newCreateTime = convertDate(comment.createTime)
            if (comment.reply && comment.reply.enabled) {
              comment.isReply = true
              comment.reply.newCreateTime = convertDate(comment.reply.createTime)
            } else {
              comment.isReply = false
            }
          })
          this.commentTotal = response.data.total
          this.commentData = comments
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.header-box {
  height: 38px;
  padding: 0 24px;
  margin-bottom: 8px;
  line-height: 38px;
  background: #fff;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
}
.return-box {
  cursor: pointer;
}
.return-box:hover {
  color: #1890ff;
}
.return-label {
  margin-left: 4px;
}
.article-box {
  max-height: 200vh;
  min-height: 80vh;
  margin-bottom: 8px;
  background: #fff;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  overflow: auto;
}
.article-content-box {
  padding: 24px;
   h1 {
    font-size: 28px;
  }
}
.article-info-box {
  margin-top: 6px;
  margin-bottom: 12px;
  padding-bottom: 16px;
  color: #999aaa;
  border-bottom: 1px solid #f0f0f0;
}
.date {
  margin-right: 16px;
}
.article-content {
  padding-top: 12px;
  padding-bottom: 28px;
}
.comment-box {
  padding: 12px 24px;
  border-top: 1px solid #d7d7d7;
  h4 {
    margin-bottom: 12px;
    font-size: 20px;
  }
}
.comment-content-box,
.publish-comment-box {
  padding: 16px 0;
}
.comment-content-box {
  padding-bottom: 32px;
  border-bottom: 1px solid #f0f0f0;
}
.btn {
  width: 200px;
}
.reset-btn {
  margin-right: 14px;
}
.ant-comment:not(:last-child) {
  padding-bottom: 12px;
  border-bottom: 1px solid #f0f0f076;
}

.comment-content-box .author {
  margin-right: 15px;
  color: #95a5a6;
  font-size: 14px;
  font-weight: bold;
}
.comment-content-box .date {
  color: #bdbdbd;
  font-size: 12px;
}
.comment-content {
  line-height: 26px;
  font-size: 16px;
  color: #34495e;
  overflow: hidden;
}
.description-count {
  font-size: 12px;
  color: rgba(0, 0, 0, 0.2);
}
</style>