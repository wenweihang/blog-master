<template>
  <div>
    <div v-if="articleTotal > 0" class="article-list">
      <div v-for="article in articlesData" :key="article.id" @click="onArticleDetail(article.id)" class="article-item-box">
        <a-skeleton active :loading="loading">
          <h3>
            <a-tag color="red">{{ article.name }}</a-tag>
            <span>{{ article.title }}</span>
          </h3>
          <p class="content">
            <a href="#" v-html="article.content"></a>
          </p>
          <div class="info-box">
            <span class="date">{{ article.newCreateTime }}</span>
            <span class="read-num">
              <a-icon type="eye" theme="filled" style="color: #ccccd7"/>
              {{ article.views }}
            </span>
          </div>
        </a-skeleton>
      </div>
    </div>
    <div v-else class="data-kong">
      <h5>童鞋，木有找到相关内容~</h5>
      <p>博主是一个很懒的人!</p>
    </div>
    <div v-if="articleTotal > 0" class="pagination-box">
      <a-pagination :current="pagination.current" :total="pagination.total" :page-size="pagination.pageSize" @change="onPageChange" />
    </div>
  </div>
</template>

<script>
import { searchArticles, getArticleByCategoryId, getArticleByArchiveDate } from '@/api/article'
import { escapeStringToHtml } from '@/utils/escapeStringAndHtml'
import { formatDate } from '@/utils/date'

export default {
  mounted() {
    this.getArticlesDate()
  },
  data() {
    return {
      pagination: {
        total: 0,
        pageSize: 8,
        current: 1
      },
      articlesData: [],
      loading: false,
      articleTotal: 0
    }
  },
  methods: {
    onArticleDetail(id) {
      this.$router.replace(`/article/${id}`)
    },
    getArticlesDate() {
      this.loading = true
      const status = this.$store.state.status
      const queryParam = this.$store.state.queryParam
      if (status == 'search') {
        searchArticles(queryParam, this.pagination.current, this.pagination.pageSize).then(response => {
          if (response && response.data) {
            this.handleDate(response.data)
          }
        })
      } else if (status == 'category') {
        getArticleByCategoryId(queryParam, this.pagination.current, this.pagination.pageSize).then(response => {
          if (response && response.data) {
            this.handleDate(response.data)
          }
        })
      } else if (status == 'archive') {
        getArticleByArchiveDate(queryParam, this.pagination.current, this.pagination.pageSize).then(response => {
          if (response && response.data) {
            this.handleDate(response.data)
          }
        })
      }
    },
    handleDate(data) {
      const articles = data.list
      this.articleTotal = data.list.length
      articles.forEach(article => {
        const newCreateTime = formatDate(article.createTime)
        const content = escapeStringToHtml(article.content).replace(/<[^>]+>/g, '')
        article.newCreateTime = newCreateTime
        article.content = content
      })
      this.loading = false
      this.pagination.total = data.total
      this.articlesData = articles
    },
    onPageChange(page) {
      this.pagination.current = page
      this.getArticlesDate()
    }
  },
  watch: {
    '$store.state.queryParam': function() {
      this.getArticlesDate()
    }
  }
}
</script>

<style lang="scss" scoped>
.article-item-box {
  margin-bottom: 8px;
  padding: 12px 22px;
  background-color: #fff;
  cursor: pointer;
}
.article-item-box:hover {
  background-color: #f9fafc;
}
.article-item-box:hover > h3 {
  color: #188fffa8;
}
.article-item-box > h3 {
  font-size: 22px;
}
.content {
  margin-top: 8px;
}
.content a {
  display: -webkit-box;
  font-size: 14px;
  line-height: 22px;
  color: #999aaa;
  overflow: hidden;
  white-space: normal;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.info-box {
  margin-top: 10px;
}
.date,
.read-num {
  color: #5e5e5e;
}
.read-num {
  margin-left: 16px;
}
.pagination-box {
  margin-top: 16px;
  text-align: center;
}
</style>
