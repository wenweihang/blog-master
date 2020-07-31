<template>
  <a-layout-sider width="300">
    <div id="asideProfile" class="aside-box">
      <a-skeleton active :loading="profileLoading">
        <img class="avatar" :src="userDetail.avatar" alt="blog-avatar"/>
        <div class="intro">简介: {{ userDetail.intro }}</div>
        <div class="data-info">
          <dl>
            <dd class="data-info-title">文章数</dd>
            <dt class="data-info-number">{{ count }}</dt>
          </dl>
          <dl>
            <dd class="data-info-title">博龄</dd>
            <dt class="data-info-number">{{ userDetail.blogAge }}天</dt>
          </dl>
        </div>
        <a class="github" href="https://github.com/liuxuelian0929" target="_blank">去博主GitHub看看</a>
      </a-skeleton>
    </div>
    <div v-if="JSON.stringify(archives) != '{}'" id="asideArchive" class="aside-box">
      <a-skeleton active :loading="archiveLoading">
        <h3 class="aside-title">归档</h3>
        <div class="aside-content">
          <div class="archive-box" v-for="(archive, key) in archives" :key="key">
            <div class="archive-title">
              {{ key }}
            </div>
            <div class="archive-content">
              <div class="archive-item" v-for="(item, index) in archive" :key="index" @click="onArchive(item.rawMonth, key)">
                  <span class="time">{{ item.month }}月</span>
                  <span class="count">{{ item.count }}篇</span>
              </div>
            </div>
          </div>
        </div>
      </a-skeleton>
    </div>
    <div v-if="categories.length > 0" id="asideCategory" class="aside-box">
      <a-skeleton active :loading="categoryLoading">
        <h3 class="aside-title">分类专栏</h3>
        <div class="aside-content">
          <ul>
            <li v-for="category in categories" :key="category.id" @click="onCategory(category.id)">
              <span class="title">{{ category.name }}</span>
              <span class="count">{{ category.count }}篇</span>
            </li>
          </ul>
        </div>
      </a-skeleton>
    </div>
  </a-layout-sider>
</template>

<script>
import { getArticleCount, getArticleCategories, getArchive } from '@/api/article'
import { getUserDetail } from '@/api/user'

export default {
  data() {
    return {
      count: 0,
      userDetail: {},
      categories: [],
      archives: {},
      profileLoading: false,
      archiveLoading: false,
      categoryLoading: false
    }
  },
  mounted() {
    this.profileLoading = true
    this.archiveLoading = true
    this.categoryLoading = true
    this.getCount()
    this.getUserDetailData()
    this.getArticleCategoryData()
    this.getArticleByMonthData()
  },
  methods: {
    getCount() {
      getArticleCount().then(response => {
        if (response && response.data) {
          this.count = response.data
        }
      })
    },
    getUserDetailData() {
      getUserDetail().then(response => {
        if (response && response.data) {
          const userDetail = response.data
          // 计算创建博客日期到现在的天数
          const createBlogDate = userDetail.createTime
          const now = Date.now();
          userDetail.blogAge = Math.round((now - createBlogDate) / ((1000 * 60 * 60 * 24)))
          this.userDetail = userDetail
          this.profileLoading = false
        }
      })
    },
    getArticleCategoryData() {
      getArticleCategories().then(response => {
        if (response && response.data) {
          this.categories = response.data.list
          this.categoryLoading = false
        }
      })
    },
    onCategory(id) {
      if (this.$router.currentRoute.fullPath != '/article') {
        this.$router.replace('/')
      }
      this.$store.commit('edit', { status: 'category', queryParam: id })
    },
    getArticleByMonthData() {
      getArchive().then(response => {
        if (response && response.data) {
          const list = response.data.list
          const archives = {}
          list.forEach(item => {
            const time = item.archiveTime
            const year = time.slice(0, 4)
            const month = time.slice(5).startsWith(0) ? time.slice(6) : time.slice(5)
            archives[year] = archives[year] || []
            archives[year].push({
              month,
              count: item.count,
              rawMonth: time.slice(5)
            })
          })
          this.archiveLoading = false
          this.archives = archives
        }
      })
    },
    onArchive(rawMonth, key) {
      if (this.$router.currentRoute.fullPath != '/article') {
        this.$router.replace('/')
      }
      this.$store.commit('edit', { status: 'archive', queryParam: key + '-' + rawMonth })
    }
  }
}
</script>

<style lang="scss" scoped>
.ant-layout-sider {
  background: transparent;
}

.aside-box {
  width: 100%;
  margin-bottom: 8px;
  background: #fff;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.05);
}

#asideProfile {
  padding: 20px;
  text-align: center;
}

.avatar {
  display: inline-block;
  width: 130px;
  height: 130px;
  margin-bottom: 16px;
}

.intro {
  padding-bottom: 12px;
  margin-bottom: 12px;
  color: rgba(0,0,0,0.35);
  border-bottom: 1px solid rgb(240, 240, 240);
}

.data-info {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 10px;
}

.data-info-title {
  font-size: 16px;
  color: rgba(0,0,0,0.85);
}

.data-info-number {
  color: rgba(0,0,0,0.35);
}

.github {
  display: inline-block;
  padding: 8px 36px;
  line-height: 1.5;
  color: #fff;
  letter-spacing: 1px;
  border-radius: 24px;
  background: #1890ff;
  text-shadow: 0 -1px 0 rgba(0,0,0,.12);
  box-shadow: 0 2px 0 rgba(0,0,0,.045);
}

.github:hover {
  background: #188fffcd;
}

.aside-title {
  padding: 0 16px;
  height: 38px;
  line-height: 38px;
  background: #188fff;
  color: #fff;
}

.aside-content {
  padding: 12px 6px 16px 16px;
}

.archive-box {
  max-height: 248px;
  margin-right: -4px;
  margin-left: -3px;
  overflow: hidden;
  overflow-y: auto;
}
.archive-title {
  padding: 0 6px;
  margin-bottom: 8px;
  line-height: 22px;
  font-size: 16px;
  color: rgba(0,0,0,0.85);
}
.archive-content {
  display: flex;
  flex-flow:row wrap;
}
.archive-item {
  width: 56px;
  margin: 0 6px 12px;
  cursor: pointer;
}
.archive-item span {
  display: block;
}
.archive-item span.time {
  height: 22px;
  margin-bottom: 1px;
  color: #999aaa;
  line-height: 22px;
  text-align: center;
  background-color: #edf0f3;
  border-radius: 6px 6px 0 0;
  opacity: .8;
}
.archive-item span.count {
  height: 22px;
  color: #555666;
  line-height: 20px;
  text-align: center;
  font-weight: 500;
  background-color: #f6f8fa;
  border-radius: 0 0 6px 6px;
  opacity: .8;
}
.archive-item span.count:hover {
  color: #1890ff;

}
#asideCategory ul li {
  display: flex;
  justify-content: space-between;
  padding: 6px 0;
  padding-right: 10px;
  cursor: pointer;
}
#asideCategory ul li span {
  display: block;
}
#asideCategory ul li:hover span.title {
  color: #1890ff;
}
</style>