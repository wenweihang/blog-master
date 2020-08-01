<template>
  <div id="home">
    <h2>数据统计</h2>
    <div class="number-block-box">
      <div class="number-block-item">
        <a-skeleton active :loading="currentLoading">
          <p class="number-info-box">
            <span class="num">{{ currentUv }}</span>
            <span class="title">今日访问量</span>
          </p>
        </a-skeleton>
      </div>
      <div class="number-block-item">
        <a-skeleton active :loading="currentLoading">
          <p class="number-info-box">
            <span class="num">{{ currentComments }}</span>
            <span class="title">今日评论数</span>
          </p>
        </a-skeleton>
      </div>
      <div class="number-block-item">
        <a-skeleton active :loading="articleLoading">
          <p class="number-info-box">
            <span class="num">{{ articleCount }}</span>
            <span class="title">文章总数</span>
          </p>
        </a-skeleton>
      </div>
      <div class="number-block-item">
        <a-skeleton active :loading="amountLoading">
          <p class="number-info-box">
            <span class="num">{{ amountUv }}</span>
            <span class="title">总访问量</span>
          </p>
        </a-skeleton>
      </div>
      <div class="number-block-item">
        <a-skeleton active :loading="commentLoading">
          <p class="number-info-box">
            <span class="num">{{ commentCount }}</span>
            <span class="title">总评论数</span>
          </p>
        </a-skeleton>
      </div>
    </div>
    <div class="views">
      <div class="data-block-item">
        <p class="title">访问量（最近7天）</p>
        <AreaGraph />
      </div>
    </div>
    <div class="data-block-box fl-grid">
      <div class="data-block-item">
        <p class="title">最新评论</p>
        <NewestComment />
      </div>
      <div class="data-block-item ml">
        <p class="title">分类文章数</p>
        <RadarGraph />
      </div>
    </div>
  </div>
</template>

<script>
import { getCookie } from '@/utils/support'
import { message } from 'ant-design-vue'
import { getUsers } from '@/api/user'
import { getAticleCount, getCommentCount } from '@/api/data'
import { getCurrentDay, getUvAmount } from '@/api/uv'
import AreaGraph from './components/AreaGraph'
import RadarGraph from './components/RadarGraph'
import NewestComment from './components/NewestComment'

export default {
  name: 'Home',
  components: {
    AreaGraph,
    RadarGraph,
    NewestComment
  },
  data() {
    return {
      currentUv: 0,
      currentComments: 0,
      currentLoading: false,
      amountLoading: false,
      amountUv: 0,
      commentCount: 0,
      commentLoading: false,
      articleLoading: false,
      articleCount: 0
    }
  },
  created() {
    const username = getCookie('username')
    const token = getCookie('token')
    if (!token) return
    if (username == null) {
      message.error('未登录,请登录')
      this.$router.replace('/login')
    } else {
      // 刷新重新获取用户信息
      getUsers(username).then(response => {
        if (response && response.data) {
          const data = response.data
          if (data.list.length > 0) {
            const user = data.list[0]
            this.$store.commit('SET_USERNAME', user.username)
            this.$store.commit('SET_AVATAR', user.avatar)
          } else {
            message.error('获取用户信息失败')
          }
        }
      })
    }
  },
  mounted() {
    this.currentLoading = true
    this.amountLoading = true
    this.articleLoading = true
    this.commentLoading = true
    this.getCurrentDayDate()
    this.getUvAmountDate()
    this.getAticleCountData()
    this.getCommentCountData()
  },
  methods: {
    getCurrentDayDate() {
      getCurrentDay().then(response => {
        if (response && response.data) {
          const data = response.data
          this.currentUv = data.uv
          this.currentComments = data.comments
          this.currentLoading = false
        }
      })
    },
    getUvAmountDate() {
      getUvAmount().then(response => {
        if (response && response.data) {
          const data = response.data
          this.amountUv = data.uv
          this.amountLoading = false
        }
      })
    },
    getAticleCountData() {
      getAticleCount().then(response => {
        if (response && response.data) {
          this.articleCount = response.data
          this.articleLoading = false
        }
      })
    },
    getCommentCountData() {
      getCommentCount().then(response => {
        if (response) {
          this.commentCount = response.data
          this.commentLoading = false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
#home {
  min-width: 1100px;
  padding: 16px;
  color: #38495c;
  h2 {
    margin-bottom: 16px;
    color: #38495c;
  font-weight: bold;
  }
}
.number-block-box {
  display: grid;
  grid-template-columns: 20% 20% 20% 20% 20%;
  grid-template-rows: 100px;
  margin-bottom: 16px;
}
.number-block-item {
  margin-right: 16px;
  padding: 16px 24px;
  border-radius: 2px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .12);
}
.number-info-box {
  span {
    display: block;
  }
  .num {
    margin-bottom: 12px;
    font-size: 22px;
    font-weight: bold;
  }
  .title {
    font-size: 16px;
  }
}
.views {
  width: calc(100% - 10px);
}
.data-block-box {
  height: 580px;
}
.data-block-item {
  padding: 16px 24px;
  border-radius: 2px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .12);
}
.data-block-item .title {
  margin-bottom: 24px;
  font-size: 16px;
  font-weight: bold;
}
.fl-grid {
  display: grid;
  grid-template-columns: 50% calc(50% - 10px);
  margin-top: 16px;
}
.ml {
  margin-left: 10px;
}
</style>
