<template>
  <div class="newest-comment">
    <a-skeleton active :loading="loading">
      <template v-if="commentTotal > 0">
        <div class="newest-comment-item" v-for="comment in comments" :key="comment.id">
        <span class="nickname">
          <a-tag color="pink">
            {{ comment.visitorNickname }}
          </a-tag></span>
        <span class="content">{{ comment.content }}</span>
        <span class="date">{{ comment.newCreateTime }}</span>
      </div>
      </template>
      <div v-else class="data-kong">
        <h5>博主，木有找到相关内容~</h5>
        <p>快去提高访问量吧!</p>
    </div>
    </a-skeleton>
  </div>
</template>

<script>
import { getCommentsLimitEight } from '@/api/data'
import { convertDate } from '@/utils/date'

export default {
  name: 'NewestComment',
  mounted() {
    this.loading = true
    this.getCommentData()
  },
  data() {
    return {
      comments: [],
      commentTotal: 0,
      loading: false
    }
  },
  methods: {
    getCommentData() {
      getCommentsLimitEight().then(response => {
        if (response && response.data) {
          const data = response.data
          const comments = data.list
          comments.forEach(comment => {
            comment.newCreateTime = convertDate(comment.createTime)
          })
          this.comments = comments
          this.commentTotal = comments.length
          this.loading = false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.newest-comment-item {
  display: grid;
  grid-template-columns: 20% auto 18%;
  padding: 16px 4px;
  margin-bottom: 4px;

  .content,
  .nickname {
    display: inline-block;
    padding: 0 4px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
}
.newest-comment-item:nth-child(odd) {
  background: #fafafa;
}
</style>