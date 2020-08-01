<template>
  <div class="write">
    <div class="write-header">
      <div class="return" @click="onReturnHome">
        <a-icon type="left" />
        <span>首页</span>
      </div>
      <div class="title-input">
        <a-input
          v-model="title"
          placeholder="输入文章标题"
          size="large"
          :maxLength="100"
          @change="onTitleChange"
        />
        <span class="title-count">
          <span class="red">{{ this.title.length }}</span>
          <span>/100</span>
        </span>
      </div>
      <div class="category">
        <a-select
          :value="currentCategory"
          style="width: 130px"
          @change="handleAssignCategoryChange"
          placeholder="选择分类"
          size="large"
        >
          <a-select-option v-for="category in allCategory" :key="category.name">
            {{ category.name }}
          </a-select-option>
        </a-select>
      </div>
      <div class="btn">
        <a-button class="save-draft-btn" size="large" @click="onSaveDraft"
          >保存草稿</a-button
        >
        <a-button type="primary" size="large" @click="onPulishArticle"
          >发布文章</a-button
        >
      </div>
    </div>
    <div class="edit_container">
      <quill-editor
        v-model="content"
        ref="myQuillEditor"
        :options="editorOption"
        @blur="onEditorBlur($event)"
        @focus="onEditorFocus($event)"
        @change="onEditorChange($event)"
      >
      </quill-editor>
    </div>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
import { quillEditor } from 'vue-quill-editor' // 调用编辑器
import {
  escapeStringToHtml,
  escapeHtmlToString
} from '@/utils/escapeStringAndHtml'
import { saveArticle, getArticleById, updateArticle } from '@/api/article'
import {
  getCategoryByArticleId,
  addCategoryForArticle
} from '@/api/articleCategoryRef'
import { getCategories } from '@/api/category'

export default {
  name: 'Write',
  components: {
    quillEditor
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill
    }
  },
  mounted() {
    const id = this.$route.params.id
    // 获取所有分类
    getCategories().then(response => {
      if (response && response.code === 200) {
        this.allCategory = response.data != null ? response.data.list : []
        if (!id) {
          this.currentCategory = this.allCategory[0].name
        }
      }
    })
    if (id) {
      this.currentArticleId = id
      getArticleById(id).then(response => {
        const data = response.data
        this.currentArticle = data
        this.title = data.title
        this.content = escapeStringToHtml(data.content)
      })
      // 获取当前文章的分类
      getCategoryByArticleId(id).then(response => {
        if (response && response.code === 200) {
          this.currentCategory = response.data != null ? response.data.name : ''
        }
      })
    }
  },
  data() {
    return {
      title: '用markdown编写',
      content: '<p>输入文章内容</p>',
      currentArticleId: -1,
      currentArticle: {},
      editorOption: {
        placeholder: '请在这里输入',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // 加粗，斜体，下划线，删除线
            ['blockquote', 'code-block'], // 引用，代码块
            [{ header: 1 }, { header: 2 }], // 标题，键值对的形式；1、2表示字体大小
            [{ list: 'ordered' }, { list: 'bullet' }], // 列表
            [{ script: 'sub' }, { script: 'super' }], // 上下标
            [{ indent: '-1' }, { indent: '+1' }], // 缩进
            [{ direction: 'rtl' }], // 文本方向
            [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
            [{ header: [1, 2, 3, 4, 5, 6, false] }], // 几级标题
            [{ color: [] }, { background: [] }], // 字体颜色，字体背景颜色
            [{ font: [] }], // 字体
            [{ align: [] }], // 对齐方式
            ['clean'], // 清除字体样式
            ['image', 'video'] // 上传图片、上传视频
          ]
        }
      },
      // 分类
      currentCategory: '',
      allCategory: []
    }
  },
  methods: {
    getArtcile(status) {
      const article = {
        title: this.title,
        content: escapeHtmlToString(this.content),
        status: status,
        allowComment: 1,
        createTime: new Date(),
        lastUpdateTime: new Date(),
        sort: 1
      }
      return article
    },
    onInsertArticle(status, msg) {
      const article = this.getArtcile(status)
      if (this.currentArticleId == -1) {
        saveArticle(article).then(response => {
          this.currentArticleId = response.data
          this.handleAssignCategoryOk()
          message.success(msg)
          this.$router.go(-1)
        })
      } else {
        this.currentArticle.title = this.title
        this.currentArticle.content = escapeHtmlToString(this.content)
        this.currentArticle.status = status
        updateArticle(this.currentArticle).then(response => {
          this.handleAssignCategoryOk()
          message.success(msg)
          this.currentArticleId = -1
          this.$router.go(-1)
        })
      }
    },
    onSaveDraft() {
      const valid = this.validateTitle()
      if (!valid) return
      this.onInsertArticle(0, '文章保存成功')
    },
    onPulishArticle() {
      const valid = this.validateTitle()
      if (!valid) return
      this.onInsertArticle(1, '文章发布成功')
    },
    validateTitle() {
      const length = this.title.length
      if (length <= 0) {
        message.info('文章标题不能为空')
        return false
      }
      return true
    },
    onReturnHome() {
      this.$router.push('/home')
    },
    onTitleChange() {},
    // 准备编辑器
    onEditorReady(editor) {},
    onEditorBlur() {}, // 失去焦点事件
    onEditorFocus() {}, // 获得焦点事件
    // 内容改变事件
    onEditorChange() {},
    // 改变分类
    handleAssignCategoryChange(value) {
      this.currentCategory = value
    },
    handleAssignCategoryOk() {
      addCategoryForArticle(this.currentArticleId, this.currentCategory).then(
        response => {
          if (response && response.code === 200) {
            this.currentCategory = ''
          }
        }
      )
    }
  }
}
</script>

<style lang="scss" scoped>
.write {
  width: 100%;
  height: 100%;
  padding: 24px;
  background: #f0f2f5;
}

.write-header {
  display: grid;
  grid-template-columns: 60px auto 140px 200px;
  width: 100%;
  height: 56px;
  margin-bottom: 16px;
}

.return,
.category,
.title-input {
  line-height: 56px;
}

.return {
  cursor: pointer;
  color: rgba(0, 0, 0, 0.45);

  .anticon-left {
    font-size: 18px;
    font-weight: 600;
  }

  span {
    margin-left: 4px;
    font-size: 18px;
    font-weight: 600;
  }
}

.title-input {
  position: relative;
  padding: 0 8px;

  .ant-input-lg {
    padding-right: 76px;
  }

  .title-count {
    position: absolute;
    right: 14px;
    font-size: 16px;
    color: rgba(0, 0, 0, 0.65);
  }

  .red {
    margin-right: 2px;
    color: #ca0c16;
  }
}

.btn {
  display: flex;
  align-items: center;
  padding: 0 8px;
}

.save-draft-btn {
  margin-right: 12px;
  border-color: #1890ff;
  color: #1890ff;
}

.save-draft-btn:hover {
  background: #1890ff;
  color: #fff;
}
</style>
